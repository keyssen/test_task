package com.task.mediasoft.configuration.properties;

import com.task.mediasoft.configuration.properties.model.MethodsProperties;
import com.task.mediasoft.configuration.properties.model.MockProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс для конфигурации свойств приложения, связанных с веб-сервисом для работы с валютами.
 * Свойства задаются в файле application.yaml с префиксом "rest.currency-service".
 */
@Configuration
@ConfigurationProperties(prefix = "rest.currency-service")
@Getter
@Setter
public class ConfigProperties {

    /**
     * Модель настроек мок-сервиса.
     */
    private MockProperties mock;

    /**
     * Хост второго сервиса для работы с валютами.
     */
    private String host;

    /**
     * Модель настроек методов для работы с валютами.
     */
    private MethodsProperties methods;
}