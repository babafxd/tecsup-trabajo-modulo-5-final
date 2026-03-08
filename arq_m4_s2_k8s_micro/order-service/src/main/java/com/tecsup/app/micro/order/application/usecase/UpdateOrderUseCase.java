package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.event.OrderUpdatedEvent;
import com.tecsup.app.micro.order.domain.exception.OrderNotFoundException;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.shared.infraestructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateOrderUseCase {

    private final OrderRepository orderRepository;
    private final KafkaEventPublisher eventPublisher;

    @Transactional
    public Order execute(Long id, Order.OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("order-service event: OrderId not found:" + id));

        order.setStatus(status);

        Order saved = orderRepository.save(order);
        if (saved.isConfirmed()) {

            eventPublisher.publish(
                    new OrderUpdatedEvent(
                            saved.getId(),
                            saved.getOrderNumber(),
                            saved.getUserId(),
                            saved.getStatus().toString(),
                            saved.getTotalAmount()
                    )
            );

        }


        return saved;
    }

}
