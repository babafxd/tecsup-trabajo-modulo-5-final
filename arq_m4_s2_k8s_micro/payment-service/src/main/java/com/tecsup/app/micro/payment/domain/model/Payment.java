package com.tecsup.app.micro.payment.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;
    private Long enrollmentId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paidAt;

    public boolean isValidAmount() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isValidEnrollmentId() {
        return (enrollmentId > 0);
    }

    public boolean isPaymentApproved() {
        return amount != null && amount.compareTo(new BigDecimal("100")) >= 0;
    }

    public enum PaymentStatus {
        APPROVED
    }
}
