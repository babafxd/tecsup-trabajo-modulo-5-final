package com.tecsup.app.micro.payment.domain.event;

import com.tecsup.app.micro.payment.shared.domain.event.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentApprovedEvent extends DomainEvent {

    private Long paymentId;
    private Long enrollmentId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime paidAt;

    public PaymentApprovedEvent(Long paymentId, Long enrollmentId, BigDecimal amount,String status, LocalDateTime paidAt) {
        super();
        this.paymentId = paymentId;
        this.enrollmentId = enrollmentId;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
    }


    @Override
    public String getKey() {
        return this.paymentId.toString();
    }
}
