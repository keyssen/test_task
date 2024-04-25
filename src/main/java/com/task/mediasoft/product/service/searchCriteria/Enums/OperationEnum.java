package com.task.mediasoft.product.service.searchCriteria.Enums;

/**
 * Перечисление, представляющее операции для использования в запросах.
 */
public enum OperationEnum {
    /**
     * Операция равенства.
     */
    EQUAL,
    /**
     * Операция "больше или равно".
     */
    GREATER_THAN_OR_EQ,
    /**
     * Операция "меньше или равно".
     */
    LESS_THAN_OR_EQ,
    /**
     * Операция "подобно" (LIKE).
     */
    LIKE
}
