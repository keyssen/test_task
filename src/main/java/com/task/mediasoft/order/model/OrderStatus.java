package com.task.mediasoft.order.model;

/**
 * Перечисление для статусов заказа.
 */
public enum OrderStatus {
    CREATED,    // Создан
    PROCESSING, // В процессе
    CONFIRMED,  // Подтвержден
    CANCELLED,  // Отменен
    DONE,       // Выполнен
    REJECTED    // Отклонен
}