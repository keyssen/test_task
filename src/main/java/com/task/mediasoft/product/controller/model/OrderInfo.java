package com.task.mediasoft.product.controller.model;

import com.task.mediasoft.order.model.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
/**
 * Класс, представляющий информацию о заказе.
 */
public class OrderInfo {

    /**
     * Уникальный идентификатор заказа.
     */
    final UUID id;

    /**
     * Информация о клиенте, сделавшем заказ.
     */
    final CustomerInfo customer;

    /**
     * Статус заказа.
     */
    final OrderStatus status;

    /**
     * Адрес доставки заказа.
     */
    final String deliveryAddress;

    /**
     * Количество заказанных товаров.
     */
    final Long quantity;
}