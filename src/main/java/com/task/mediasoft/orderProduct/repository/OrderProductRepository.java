package com.task.mediasoft.orderProduct.repository;

import com.task.mediasoft.order.model.dto.ViewProductFromOrderDTO;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.orderProduct.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с товарами в заказе.
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    /**
     * Поиск всех товаров в заказе по идентификатору заказа.
     *
     * @param orderId идентификатор заказа
     * @return список DTO товаров, представляющих товары в заказе
     */
    @Query("SELECT new com.task.mediasoft.order.model.dto.ViewProductFromOrderDTO(op.product.id, op.product.name, op.quantity, op.frozenPrice) FROM OrderProduct op WHERE op.order.id = :orderId")
    List<ViewProductFromOrderDTO> findAllViewProductsByOrderId(@Param("orderId") UUID orderId);
}