package com.task.mediasoft.order.repository;

import com.task.mediasoft.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью Order.
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByIdAndCustomer_Id(UUID id, Long customerId);
}