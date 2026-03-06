package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.exception.InsufficientStockException;
import com.tecsup.app.micro.order.domain.exception.ProductNotFoundException;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.client.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public Order execute(Order order) {
        log.debug("Executing CreateOrderUseCase for userid: {}", order.getUserId());
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus(Order.OrderStatus.PENDING);

        for (OrderItem item : order.getItems()) {

            var productInfo = productClient.getProductById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + item.getProductId()));


            // Validar stock disponible
            if (productInfo.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + productInfo.getName() + ", productId: " + productInfo.getId() + ", Stock: " + productInfo.getStock());
            }

            // Obtener precio actual y calcular subtotal del item
            item.setProduct(productInfo);
            item.setUnitPrice(productInfo.getPrice());
            item.calculateSubtotal();
        }

        order.calculateTotalAmount();
        Order savedOrder = orderRepository.save(order);

        order.setId(savedOrder.getId());
        order.setCreatedAt(savedOrder.getCreatedAt());
        order.setUpdatedAt(savedOrder.getUpdatedAt());

        for (int i = 0; i < order.getItems().size(); i++) {
            order.getItems().get(i).setId(savedOrder.getItems().get(i).getId());
        }

        log.info("Order created successfully with id: {}", savedOrder.getId());
        return order;
    }


}
