package com.tecsup.app.micro.order.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailResponse {
    private Long id;
    private String name;
    private BigDecimal price;

}
