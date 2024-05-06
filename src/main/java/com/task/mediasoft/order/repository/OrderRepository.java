package com.task.mediasoft.order.repository;

import com.task.mediasoft.order.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью Order.
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {

    /**
     * Переопределение метода поиска заказа по идентификатору с использованием EntityGraph для жадной загрузки продуктов заказа.
     *
     * @param uuid Идентификатор заказа.
     * @return Optional с заказом, если найден.
     */
    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = "orderProducts")
    Optional<Order> findById(UUID uuid);
}