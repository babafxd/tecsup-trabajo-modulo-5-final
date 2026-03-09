package com.tecsup.app.micro.delivery.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    
    private Long id;
    private Long orderId;
    private String trackingCode;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private String deliveryAddress;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum DeliveryStatus {
        READY,
        DELIVERED
    }
}
