package com.tecsup.app.micro.order.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;
    private ProductDetailResponse product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
