package com.task.mediasoft.configuration;


import com.task.mediasoft.configuration.properties.ConfigProperties;
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
    private final ConfigProperties configProperties;

    /**
     * Создает и возвращает настроенный экземпляр {@link WebClient}.
     * <p>
     * Метод использует предоставленный {@link WebClient.Builder} для настройки экземпляра WebClient
     * с базовым URL, который извлекается из свойства конфигурации "currency-service.host". Этот URL
     * используется как начальная точка для всех последующих запросов, отправляемых с помощью данного клиента.
     * </p>
     *
     * @param webClientBuilder Предоставленный {@link WebClient.Builder} для создания настроенного клиента.
     * @return Настроенный экземпляр {@link WebClient}, готовый к использованию для выполнения HTTP запросов.
     * @throws NullPointerException если свойство "currency-service.host" не установлено в конфигурации.
     */
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(configProperties.getHost()).build();
    }
}
