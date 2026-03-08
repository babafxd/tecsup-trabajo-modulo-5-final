package com.tecsup.app.micro.payment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDto {

    private Long id;
    private ProductDetailDto product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
