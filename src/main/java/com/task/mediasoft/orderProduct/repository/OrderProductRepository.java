package com.task.mediasoft.orderProduct.repository;

import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.orderProduct.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

/**
 * Репозиторий для работы с товарами в заказе.
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    /**
     * Поиск товара в заказе по идентификаторам заказа и продукта.
     *
     * @param orderId   идентификатор заказа
     * @param productId идентификатор продукта
     * @return товар в заказе, если найден, иначе null
     */
    @Query("SELECT op FROM OrderProduct op WHERE op.order.id = :orderId AND op.product.id = :productId")
    OrderProduct findOrderProduct(@Param("orderId") UUID orderId, @Param("productId") UUID productId);
}