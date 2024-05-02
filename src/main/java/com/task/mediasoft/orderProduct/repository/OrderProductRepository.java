package com.task.mediasoft.orderProduct.repository;

import com.task.mediasoft.orderProduct.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
}
