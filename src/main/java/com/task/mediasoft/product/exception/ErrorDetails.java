package com.task.mediasoft.product.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * Класс, представляющий детали ошибки.
 * Используется для хранения информации о возникшей ошибке, включая имя класса, имя исключения, сообщение и время возникновения.
 */
@Builder
@Data
@AllArgsConstructor
public class ErrorDetails {

    /**
     * Имя класса, в котором произошла ошибка.
     */
    private String className;

    /**
     * Имя исключения, которое было выброшено.
     */
    private String exceptionName;

    /**
     * Сообщение об ошибке.
     */
    private String message;

    /**
     * Время, когда возникла ошибка.
     */
    private LocalDateTime time;
}
