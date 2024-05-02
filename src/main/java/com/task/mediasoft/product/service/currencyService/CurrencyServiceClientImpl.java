package com.task.mediasoft.product.service.currencyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация интерфейса {@link CurrencyServiceClient}, предназначенная для взаимодействия с внешним API валют.
 * <p>
 * Данный класс использует {@link WebClient} для отправки HTTP запросов к внешнему сервису и получения текущих курсов валют.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient webClient;
    private final Environment env;

    /**
     * Выполняет HTTP запрос к сервису обмена валют и возвращает карту текущих курсов валют.
     * <p>
     * Метод делает HTTP GET запрос к URL, который берется из свойства конфигурации "currency-service.methods.get-currency".
     * Полученные данные о курсах валют преобразуются в карту, где ключом является код валюты (например, "USD", "EUR"), а значением - курс.
     * В случае неудачных попыток запрос будет повторен до двух раз. Метод использует кэширование ответов.
     * </p>
     *
     * @return Карта ({@link Map}) с кодами валют и их текущими курсами в формате {@link BigDecimal}.
     * В случае ошибки в запросе или обработке ответа возвращается пустая карта.
     */
    @Cacheable(cacheNames = "currencyCache")
    public Map<String, BigDecimal> someRestCall() {
        log.info("IMPLEMENTATION");
        return this.webClient.get().uri(Objects.requireNonNull(env.getProperty("currency-service.methods.get-currency")))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, BigDecimal>>() {
                })
                .retry(2)
                .block();
    }
}
