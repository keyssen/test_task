package com.task.mediasoft.product.service.currencyService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient webClient;
    private final Environment env;

    @Cacheable(cacheNames = "currencyCache")
    public Map<String, BigDecimal> someRestCall() {
        return this.webClient.get().uri(Objects.requireNonNull(env.getProperty("currency-service.methods.get-currency")))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, BigDecimal>>() {
                })
                .retry(2)
                .block();
    }
}
