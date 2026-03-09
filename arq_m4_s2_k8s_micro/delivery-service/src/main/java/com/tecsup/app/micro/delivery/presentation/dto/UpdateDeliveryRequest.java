package com.tecsup.app.micro.delivery.presentation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeliveryRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "READY|DELIVERED",
            message = "Status must be READY or DELIVERED")
    private String status;

}
