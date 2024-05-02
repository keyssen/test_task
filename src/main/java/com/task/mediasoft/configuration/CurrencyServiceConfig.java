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

/**
 * Конфигурационный класс для клиента сервиса обмена валют.
 * <p>
 * Этот класс предназначен для создания экземпляра клиента сервиса обмена валют, который может быть
 * либо реальной реализацией, взаимодействующей с внешним API, либо моковой реализацией для
 * тестирования и разработки без необходимости вызова внешнего сервиса.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class CurrencyServiceConfig {
    private final Environment env;
    private final WebClient webClient;

    /**
     * Создает и возвращает клиента сервиса обмена валют в зависимости от настроек окружения.
     * <p>
     * Этот метод проверяет свойство конфигурации "currency-service.client". Если значение этого
     * свойства равно "implementation", то создается и возвращается реализация {@link CurrencyServiceClientImpl},
     * использующая {@link WebClient} для доступа к внешнему API. В противном случае возвращается
     * моковая реализация {@link CurrencyServiceClientMock}, которая может быть использована для
     * тестирования или в разработке.
     * </p>
     *
     * @return Экземпляр {@link CurrencyServiceClient}, который может быть реальной реализацией или моком,
     * в зависимости от настроек окружения.
     * @throws NullPointerException если свойство "currency-service.client" не определено.
     */
    @Bean
    public CurrencyServiceClient currencyServiceClient() {
        if (Objects.equals(Objects.requireNonNull(env.getProperty("currency-service.client")), "implementation")) {
            return new CurrencyServiceClientImpl(webClient, env);
        }
        return new CurrencyServiceClientMock();
    }
}
