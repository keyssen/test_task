package com.task.mediasoft.orderProduct.repository;

import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.orderProduct.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    @Query("SELECT op FROM OrderProduct op WHERE op.order.id = :orderId AND op.product.id = :productId")
    OrderProduct findOrderProduct(@Param("orderId") UUID orderId, @Param("productId") UUID productId);
}
