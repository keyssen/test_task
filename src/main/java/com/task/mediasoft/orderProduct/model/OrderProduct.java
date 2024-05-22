package com.task.mediasoft.orderProduct.model;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Модель для отображения связи между заказом и продуктом.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_product")
@IdClass(OrderProductId.class)
public class OrderProduct {

    /**
     * Ссылка на заказ.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Ссылка на продукт.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Замороженная цена продукта на момент добавления в заказ.
     */
    @Column(name = "frozen_price", nullable = false)
    private BigDecimal frozenPrice;

    /**
     * Количество продуктов.
     */
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProduct that)) return false;
        return Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getFrozenPrice(), that.getFrozenPrice()) && Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getProduct(), getFrozenPrice(), getQuantity());
    }
}