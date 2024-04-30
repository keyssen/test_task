package com.task.mediasoft.product.service.currencyService;

import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class CurrencyServiceClientMock implements CurrencyServiceClient {
    private final Random random = new Random();

    @Cacheable(cacheNames = "currencyCacheMock")
    public Map<String, BigDecimal> someRestCall() {
        Map<String, BigDecimal> currencyRates = new HashMap<>();
        currencyRates.put("CNY", randomBigDecimal());
        currencyRates.put("USD", randomBigDecimal());
        currencyRates.put("EUR", randomBigDecimal());
        return currencyRates;
    }

    private BigDecimal randomBigDecimal() {
        return BigDecimal.valueOf(random.nextDouble() * 99 + 1).setScale(2, RoundingMode.HALF_UP);
    }
}