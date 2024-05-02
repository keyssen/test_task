package com.task.mediasoft.orderProduct.repository;

import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.orderProduct.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
