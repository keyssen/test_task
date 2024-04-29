package com.task.mediasoft.product.service.searchCriteria.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Перечисление, представляющее операции для использования в запросах.
 */
public enum OperationEnum {
    /**
     * Операция равенства.
     */
    EQUAL("="),
    /**
     * Операция "больше или равно".
     */
    GREATER_THAN_OR_EQ(">="),
    /**
     * Операция "меньше или равно".
     */
    LESS_THAN_OR_EQ("<="),
    /**
     * Операция "подобно" (LIKE).
     */
    LIKE("~");

    private final String code;

    /**
     * Создает новый объект перечисления с указанным кодом операции.
     *
     * @param code Код операции.
     */
    OperationEnum(String code) {
        this.code = code;
    }

    /**
     * Возвращает код операции.
     *
     * @return Код операции.
     */
    @JsonValue
    public String getCode() {
        return code;
    }

    /**
     * Возвращает объект перечисления по его коду.
     *
     * @param code Код операции.
     * @return Объект перечисления, соответствующий указанному коду, или null, если соответствующий объект не найден.
     */
    @JsonCreator
    public static OperationEnum fromCode(String code) {
        for (OperationEnum operationType : OperationEnum.values()) {
            if (operationType.name().equals(code) || operationType.code.equals(code)) {
                return operationType;
            }
        }
        return null;
    }
}
