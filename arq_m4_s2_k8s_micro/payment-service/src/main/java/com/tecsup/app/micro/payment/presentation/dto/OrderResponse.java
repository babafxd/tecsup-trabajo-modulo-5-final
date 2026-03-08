package com.tecsup.app.micro.payment.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private Long userId;
    private List<OrderItemsResponse> items;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

}
