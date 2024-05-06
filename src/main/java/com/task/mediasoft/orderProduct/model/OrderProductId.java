package com.task.mediasoft.orderProduct.model;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.product.model.Product;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


/**
 * Идентификатор для связи между заказом и продуктом.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderProductId implements Serializable {

    /**
     * Ссылка на заказ.
     */
    private Order order;

    /**
     * Ссылка на продукт.
     */
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductId that)) return false;
        return Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getProduct());
    }
}