package com.task.mediasoft.order.model;

/**
 * Перечисление для статусов заказа.
 */
public enum OrderStatus {
    CREATED,    // Создан
    CONFIRMED,  // Подтвержден
    CANCELLED,  // Отменен
    DONE,       // Выполнен
    REJECTED    // Отклонен
}