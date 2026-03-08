package com.tecsup.app.micro.order.domain.event;

import com.tecsup.app.micro.order.shared.domain.event.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentRejectedEvent extends DomainEvent {

    private Long paymentId;
    private Long orderId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime paidAt;

    public PaymentRejectedEvent(Long paymentId, Long orderId, BigDecimal amount,String status, LocalDateTime paidAt) {
        super();
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
    }

    @Override
    public String getKey() {
        return this.paymentId.toString();
    }

}
