package com.task.mediasoft.product.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.mediasoft.product.model.dto.ViewCurrenciesDto;
import com.task.mediasoft.product.service.currencyService.CurrencyServiceClient;
import com.task.mediasoft.session.CurrencyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Поставщик обменного курса.
 */
@Component
@RequiredArgsConstructor
public class ExchangeRateProvider {
    private final CurrencyServiceClient currencyServiceClient;

    /**
     * Получает обменный курс для указанной валюты.
     *
     * @param currency Код валюты.
     * @return Обменный курс для указанной валюты.
     */
    public BigDecimal getExchangeRate(CurrencyEnum currency) {
        return Optional.ofNullable(getExchangeRateFromService(currency))
                .orElseGet(() -> getExchangeRateFromFile(currency));
    }

    /**
     * Получает обменный курс для указанной валюты из веб-сервиса.
     *
     * @param currency Код валюты.
     * @return Обменный курс для указанной валюты.
     */
    private @Nullable BigDecimal getExchangeRateFromService(CurrencyEnum currency) {
        return Optional.ofNullable(currencyServiceClient.getCurrencies())
                .map(rate -> getExchangeRateByCurrency(currency, rate)).orElse(null);
    }

    /**
     * Получает обменный курс для указанной валюты из файла.
     *
     * @param currency Код валюты.
     * @return Обменный курс для указанной валюты.
     */
    private @Nullable BigDecimal getExchangeRateFromFile(CurrencyEnum currency) {
        BigDecimal currencyValue = BigDecimal.ONE;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode currencyJsonNode = objectMapper.readTree(new File("exchange-rate.json")).get(currency.name());
            if (currencyJsonNode != null) {
                currencyValue = new BigDecimal(currencyJsonNode.asText());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currencyValue;

    }

    /**
     * Возвращает обменный курс для указанной валюты.
     *
     * @param currency    Код валюты (например, "USD").
     * @param currencyDto Объект {@link ViewCurrenciesDto} с данными о курсах валют.
     * @return Обменный курс для указанной валюты.
     */
    private BigDecimal getExchangeRateByCurrency(CurrencyEnum currency, ViewCurrenciesDto currencyDto) {
        return switch (currency) {
            case USD -> currencyDto.getUSD();
            case CNY -> currencyDto.getCNY();
            case EUR -> currencyDto.getEUR();
            case RUB -> BigDecimal.ONE;
        };
    }
}