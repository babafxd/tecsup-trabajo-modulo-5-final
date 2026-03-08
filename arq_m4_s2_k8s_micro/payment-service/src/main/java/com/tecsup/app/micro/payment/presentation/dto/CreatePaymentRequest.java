package com.tecsup.app.micro.payment.presentation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull(message = "orderId is required")
    @Min(value = 1, message = "orderId must be greater than zero")
    private Long orderId;

    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount must be greater than zero")
    private BigDecimal amount;
}
