package com.task.mediasoft.product.service.currencyService;

import com.task.mediasoft.product.model.dto.ViewCurrenciesDto;

/**
 * Интерфейс для клиента службы валют.
 * Этот интерфейс определяет метод для получения данных о курсах валют.
 */
public interface CurrencyServiceClient {

    /**
     * Получает данные о курсах валют.
     *
     * @return Объект DTO с данными о курсах валют.
     */
    ViewCurrenciesDto getCurrencies();
}