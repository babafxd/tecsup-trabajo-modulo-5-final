package com.tecsup.app.micro.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    private Long id;
    private ProductDetail product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
