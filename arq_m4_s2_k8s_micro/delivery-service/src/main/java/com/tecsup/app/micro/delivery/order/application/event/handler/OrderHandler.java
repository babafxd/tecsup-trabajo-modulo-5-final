package com.tecsup.app.micro.delivery.order.application.event.handler;

import com.tecsup.app.micro.delivery.application.service.DeliveryApplicationService;
import com.tecsup.app.micro.delivery.domain.event.OrderUpdatedEvent;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.presentation.dto.CreateDeliveryRequest;
import com.tecsup.app.micro.delivery.presentation.mapper.DeliveryDtoMapper;
import com.tecsup.app.micro.delivery.shared.infraestructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        topics = KafkaConfig.ORDER_EVENTS_TOPIC,
        groupId = "delivery-order-group"
)
public class OrderHandler
{

    private DeliveryApplicationService deliveryApplicationService;
    private final DeliveryDtoMapper deliveryDtoMapper;

    @KafkaHandler
    public void handleOrderUpdated(OrderUpdatedEvent event) {
        log.info("✅ Orden actualizada, registrando delivery para la orderId {}", event.getOrderId());
        CreateDeliveryRequest request = new CreateDeliveryRequest();
        request.setOrderId(event.getOrderId());
        Delivery delivery = deliveryDtoMapper.toDomain(request);
        Delivery saved = deliveryApplicationService.createDelivery(delivery, Delivery.DeliveryStatus.READY.name());
    }
}
