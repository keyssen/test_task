package com.task.mediasoft.configuration;

import com.task.mediasoft.product.service.currencyService.CurrencyServiceClient;
import com.task.mediasoft.product.service.currencyService.CurrencyServiceClientImpl;
import com.task.mediasoft.product.service.currencyService.CurrencyServiceClientMock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class CurrencyServiceConfig {
    private final Environment env;
    private final WebClient webClient;

    @Bean
    public CurrencyServiceClient currencyServiceClient() {
        if (Objects.equals(Objects.requireNonNull(env.getProperty("currency-service.client")), "service")) {
            return new CurrencyServiceClientImpl(webClient, env);
        }
        return new CurrencyServiceClientMock();
    }
}
