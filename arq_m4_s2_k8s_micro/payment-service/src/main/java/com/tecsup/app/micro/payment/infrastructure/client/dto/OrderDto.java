package com.tecsup.app.micro.payment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String orderNumber;
    private Long userId;
    private List<OrderItemsDto> items;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
}
