package com.task.mediasoft.configuration.properties.currencyService.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель настроек методов для работы с валютами.
 */
@Getter
@Setter
public class CurrencyMethodsProperties {

    /**
     * URL эндпоинта для получения курсов валют.
     */
    private String getCurrency;
}