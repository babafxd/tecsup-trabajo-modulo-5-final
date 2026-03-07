package com.tecsup.app.micro.payment.shared.infraestructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConfig {
    public static final String PAYMENT_EVENTS_TOPIC = "lms.payment.events";

    @Bean
    public NewTopic paymentEventsTopic() {
        return new NewTopic(PAYMENT_EVENTS_TOPIC, // topic
                1,  // Nro particiones
                (short) 1  // Nro de replicas
        );
    }
}