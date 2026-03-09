package com.tecsup.app.micro.delivery.domain.event;

import com.tecsup.app.micro.delivery.shared.domain.event.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class OrderUpdatedEvent extends DomainEvent {

    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String status;
    private BigDecimal totalAmount;

    public OrderUpdatedEvent(Long orderId, String orderNumber, Long userId, String status, BigDecimal totalAmount) {
        super();
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    @Override
    public String getKey() {
        return this.orderId.toString();
    }

}
