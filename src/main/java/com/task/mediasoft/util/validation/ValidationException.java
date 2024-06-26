package com.task.mediasoft.util.validation;

import java.util.Set;

/**
 * Класс исключения, используемый для представления ошибок валидации.
 */
public class ValidationException extends RuntimeException {

    /**
     * Конструктор класса ValidationException.
     *
     * @param errors Множество строк с ошибками валидации.
     */
    public ValidationException(Set<String> errors) {
        super(String.join("\n", errors));
    }
}
