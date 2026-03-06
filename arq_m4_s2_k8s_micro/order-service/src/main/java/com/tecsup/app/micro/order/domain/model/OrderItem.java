package com.tecsup.app.micro.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private Product product;
    public void calculateSubtotal() {
        if (this.unitPrice != null && this.quantity != null) {
            this.subtotal = this.unitPrice.multiply(new BigDecimal(this.quantity));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

    public boolean isValid() {
        return productId != null && productId > 0
                && quantity != null && quantity > 0
                && unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) >= 0
                && subtotal != null && subtotal.compareTo(BigDecimal.ZERO) >= 0;
    }

}
