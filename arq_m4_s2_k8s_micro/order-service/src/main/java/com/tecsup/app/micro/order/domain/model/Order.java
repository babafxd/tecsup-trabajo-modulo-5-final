package com.tecsup.app.micro.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String orderNumber;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Enum para manejar los estados definidos en el constraint chk_status del SQL
     */
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    /**
     * Calcula el monto total sumando los subtotales de cada item
     */
    public void calculateTotalAmount() {
        if (items == null || items.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
            return;
        }
        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Valida si la orden puede ser procesada (tiene items y usuario)
     */
    public boolean isValid() {
        return userId != null && userId > 0
                && items != null && !items.isEmpty()
                && totalAmount != null && totalAmount.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Permite cancelar la orden solo si está en estado PENDING
     */
    public void cancel() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }

    /**
     * Agrega un item a la orden y recalcula el total automáticamente
     */
    public void addItem(OrderItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        calculateTotalAmount();
    }



}
