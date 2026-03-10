package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.exception.*;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.model.Order;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.delivery.infrastructure.client.OrderClient;
import com.tecsup.app.micro.delivery.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateDeliveryUseCase {

    private final DeliveryRepository deliveryRepository;
    private final OrderClient orderClient;
    private final UserClient userClient;

    public Delivery execute(Delivery delivery, String Status, String jwtToken) {
        log.debug("Executing CreateDeliveryUseCase for order id: {}", delivery.getOrderId());

        if (jwtToken.isEmpty()) {
            var token = userClient.getToken()
                    .orElseThrow(() -> new TokenNotFoundException("Token not found"));

            jwtToken = token.getToken();
        }

        var orderInfo = orderClient.getOrderById(delivery.getOrderId(), jwtToken)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for Id:" + delivery.getOrderId()));

        if (orderInfo.getStatus().equals(Order.OrderStatus.PENDING.name()) ||
                orderInfo.getStatus().equals(Order.OrderStatus.SHIPPED.name()) ||
                orderInfo.getStatus().equals(Order.OrderStatus.DELIVERED.name()
                )) {
            throw new InvalidDeliveryException("\n" + "The order is not available for delivery.");
        }

        var userInfo = userClient.getUserById(orderInfo.getUserId(), jwtToken)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + orderInfo.getUserId()));

        delivery.setDeliveryAddress(userInfo.getAddress());


        // Guardar usuario
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("Delivery created successfully with id: {}", savedDelivery.getId());

        return savedDelivery;
    }
}
