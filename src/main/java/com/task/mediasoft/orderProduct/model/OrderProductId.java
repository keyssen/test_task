package com.task.mediasoft.orderProduct.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderProductId implements Serializable {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductId that)) return false;
        return Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getProductId());
    }
}