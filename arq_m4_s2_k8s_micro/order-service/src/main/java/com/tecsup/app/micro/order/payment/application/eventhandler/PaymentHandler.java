package com.tecsup.app.micro.order.payment.application.eventhandler;

import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.domain.event.PaymentApprovedEvent;
import com.tecsup.app.micro.order.domain.event.PaymentRejectedEvent;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.shared.infraestructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        topics = KafkaConfig.PAYMENT_EVENTS_TOPIC,
        groupId = "order-payment-group"
)
public class PaymentHandler {

    private final OrderApplicationService orderApplicationService;

    @KafkaHandler
    public void handleApproved(PaymentApprovedEvent event) {
        log.info("✅ Pago Aprobado recibido: ID {} para la orderId {}", event.getPaymentId(), event.getOrderId());
        Order saved = orderApplicationService.updateOrder(event.getOrderId(), Order.OrderStatus.CONFIRMED);

    }

    @KafkaHandler
    public void handleRejected(PaymentRejectedEvent event) {
        log.error("❌ Pago Rechazado recibido: ID {} para la orderId {}", event.getPaymentId(), event.getOrderId());
        Order saved = orderApplicationService.updateOrder(event.getOrderId(), Order.OrderStatus.CANCELLED);
    }

    @KafkaHandler(isDefault = true)
    public void handleUnknown(Object event) {
        log.warn("❓ Evento de pago desconocido o no soportado: {}", event.getClass().getName());
    }

}