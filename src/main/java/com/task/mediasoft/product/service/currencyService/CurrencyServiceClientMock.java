package com.task.mediasoft.product.service.currencyService;

import com.task.mediasoft.product.model.dto.ViewCurrenciesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


/**
 * Класс-заглушка для клиента службы валюты.
 * Этот сервис используется для предоставления мок-данных о курсах валют.
 * Является первичным бином, который используется в случае, если включена опция использования мок-сервиса (включена в конфигурации приложения).
 * Кеширование данных включено для этого сервиса.
 * Данные о курсах валют генерируются случайным образом при каждом запросе.
 */
@Slf4j
@Primary
@Service
@ConditionalOnProperty(name = "rest.currency-service.mock.enabled", matchIfMissing = false)
@CacheConfig(cacheNames = {"currencyCacheMock"})
public class CurrencyServiceClientMock implements CurrencyServiceClient {

    /**
     * Объект для генерации случайных чисел.
     */
    private final Random random = new Random();

    /**
     * Получает данные о курсах валют.
     *
     * @return Объект DTO с данными о курсах валют.
     */
    @Cacheable(cacheNames = "currencyCacheMock")
    public ViewCurrenciesDto getCurrencies() {
        log.info("MOCK");
        return new ViewCurrenciesDto(randomBigDecimal(), randomBigDecimal(), randomBigDecimal());
    }

    /**
     * Генерирует случайное вещественное число в диапазоне от 1 до 100 с точностью до двух знаков после запятой.
     *
     * @return Случайное вещественное число.
     */
    private BigDecimal randomBigDecimal() {
        return BigDecimal.valueOf(random.nextDouble() * 99 + 1).setScale(2, RoundingMode.HALF_UP);
    }
}