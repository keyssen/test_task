package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.order.model.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) для представления информации о заказе с продуктами.
 */
@Data
public class ViewOrderWithProductDTO {

    /**
     * Идентификатор заказа.
     */
    private final UUID orderId;

    /**
     * Список продуктов в заказе.
     */
    private final List<ViewProductFromOrderDTO> products;

    /**
     * Общая стоимость заказа.
     */
    private final BigDecimal totalPrice;

    /**
     * Создает новый объект ViewOrderWithProductDTO на основе сущности Order.
     *
     * @param order Сущность Order, из которой извлекаются данные для представления.
     */
    public ViewOrderWithProductDTO(Order order) {
        this.orderId = order.getId();
        this.products = order.getOrderProducts().stream().map(ViewProductFromOrderDTO::new).toList();
        this.totalPrice = order.getOrderProducts().stream()
                .map(orderProduct -> orderProduct.getFrozenPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Создает новый объект ViewOrderWithProductDTO на основе указанного идентификатора заказа и списка продуктов.
     *
     * @param orderId  Идентификатор заказа.
     * @param products Список продуктов в заказе.
     */
    public ViewOrderWithProductDTO(UUID orderId, List<ViewProductFromOrderDTO> products) {
        this.orderId = orderId;
        this.products = products;
        this.totalPrice = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}