package com.tecsup.app.micro.order.shared.infraestructure.event;

import com.tecsup.app.micro.order.domain.event.OrderUpdatedEvent;
import com.tecsup.app.micro.order.shared.domain.event.DomainEvent;
import com.tecsup.app.micro.order.shared.infraestructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
    private String getTopicFromEvent(DomainEvent event) {

        if (event instanceof OrderUpdatedEvent) {
            return KafkaConfig.ORDER_EVENTS_TOPIC;
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
        }
    }

    public void publish(DomainEvent event) {
        log.info("Publicando: {} [{}]", event.getEventType(), event.getEventId());
        String topic = getTopicFromEvent(event);
        String key = event.getKey(); // devuelva el course Id

        kafkaTemplate.send(
                topic,
                key,
                event
        );
    }

}
