package com.task.mediasoft.configuration;


import com.task.mediasoft.configuration.properties.accountService.AccountServiceProperties;
import com.task.mediasoft.configuration.properties.camundaService.CamundaServiceProperties;
import com.task.mediasoft.configuration.properties.crmService.CrmServiceProperties;
import com.task.mediasoft.configuration.properties.currencyService.CurrencyServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Конфигурационный класс для настройки {@link WebClient}.
 * <p>
 * Этот класс предназначен для создания настроенного экземпляра {@link WebClient}, который
 * используется для выполнения HTTP запросов к внешним сервисам. Конфигурация включает в себя
 * базовый URL, который берется из переменных окружения приложения.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final AccountServiceProperties accountServiceProperties;
    private final CrmServiceProperties crmServiceProperties;
    private final CurrencyServiceProperties currencyServiceProperties;
    private final CamundaServiceProperties camundaServiceProperties;

    @Bean
    public WebClient accountServiceWebClient(WebClient.Builder webClientBuilder) {
        System.out.println("Initializing WebClient with host: ");
        return webClientBuilder.baseUrl(accountServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient crmServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(crmServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient currencyServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(currencyServiceProperties.getHost()).build();
    }

    @Bean
    public WebClient camundaServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(camundaServiceProperties.getHost()).build();
    }
}