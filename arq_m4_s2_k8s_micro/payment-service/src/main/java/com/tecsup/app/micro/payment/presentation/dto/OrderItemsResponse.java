package com.tecsup.app.micro.payment.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsResponse {

    private Long id;
    private ProductDetailResponse product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
