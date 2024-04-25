package com.task.mediasoft.product.service.searchCriteria;

import com.task.mediasoft.product.service.searchCriteria.Enums.OperationEnum;

/**
 * Утилитный класс, предоставляющий методы для преобразования строковых операций в перечисление OperationEnum.
 */
public class CriteriaMethods {

    /**
     * Преобразует строковое представление операции в соответствующее значение перечисления OperationEnum.
     *
     * @param operation строковое представление операции
     * @return соответствующее значение перечисления OperationEnum
     */
    public static OperationEnum operationToEnum(String operation) {
        switch (operation) {
            case "=":
                return OperationEnum.EQUAL;
            case ">=":
                return OperationEnum.GREATER_THAN_OR_EQ;
            case "<=":
                return OperationEnum.LESS_THAN_OR_EQ;
            case "~":
                return OperationEnum.LIKE;
            default:
                return OperationEnum.valueOf(operation);
        }
    }
}
