package com.task.mediasoft.configuration.properties.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель настроек методов для работы с валютами.
 */
@Getter
@Setter
public class MethodsProperties {

    /**
     * URL эндпоинта для получения курсов валют.
     */
    private String getCurrency;
}