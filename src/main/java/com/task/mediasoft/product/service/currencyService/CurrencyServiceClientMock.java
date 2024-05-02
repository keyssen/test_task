package com.task.mediasoft.product.service.currencyService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Моковая реализация интерфейса {@link CurrencyServiceClient} для использования в тестировании или разработке.
 * <p>
 * Этот класс имитирует получение курсов валют, генерируя случайные значения. Он используется для обеспечения
 * независимости разработки и тестирования от внешних сервисов.
 * </p>
 */
@Slf4j
public class CurrencyServiceClientMock implements CurrencyServiceClient {
    private final Random random = new Random();

    @Cacheable(cacheNames = "currencyCacheMock")
    public Map<String, BigDecimal> someRestCall() {
        log.info("MOCK");
        Map<String, BigDecimal> currencyRates = new HashMap<>();
        currencyRates.put("CNY", randomBigDecimal());
        currencyRates.put("USD", randomBigDecimal());
        currencyRates.put("EUR", randomBigDecimal());
        return currencyRates;
    }

    /**
     * Генерирует случайное десятичное значение для курса валюты.
     * <p>
     * Возвращает значение {@link BigDecimal} с двумя знаками после запятой, которое может быть использовано
     * как курс валюты. Значение варьируется от 1.00 до 99.99.
     * </p>
     *
     * @return Случайное значение {@link BigDecimal}, представляющее курс валюты.
     */
    private BigDecimal randomBigDecimal() {
        return BigDecimal.valueOf(random.nextDouble() * 99 + 1).setScale(2, RoundingMode.HALF_UP);
    }
}