package com.task.mediasoft.product.service.currencyService;

import com.task.mediasoft.configuration.properties.currencyService.CurrencyServiceProperties;
import com.task.mediasoft.product.model.dto.ViewCurrenciesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Реализация интерфейса {@link CurrencyServiceClient}, предназначенная для взаимодействия с внешним API валют.
 * <p>
 * Данный класс использует {@link WebClient} для отправки HTTP запросов к внешнему сервису и получения текущих курсов валют.
 * </p>
 */
@Slf4j
@Service
@CacheConfig(cacheNames = {"currencyCache"})
@RequiredArgsConstructor
public class CurrencyServiceClientImpl implements CurrencyServiceClient {

    /**
     * Конфигурационные свойства для взаимодействия с API валют.
     */
    private final CurrencyServiceProperties currencyServiceProperties;

    /**
     * WebClient для взаимодействия с внешним API.
     */
    private final WebClient currencyServiceWebClient;

    /**
     * Получает данные о курсах валют от внешнего API.
     *
     * @return Объект DTO с данными о курсах валют.
     */
    @Cacheable(cacheNames = "currencyCache")
    public ViewCurrenciesDto getCurrencies() {
        log.info("IMPLEMENTATION");
        return this.currencyServiceWebClient.get().uri(currencyServiceProperties.getMethods().getGetCurrency())
                .retrieve()
                .bodyToMono(ViewCurrenciesDto.class)
                .retry(2)
                .block();
    }
}
