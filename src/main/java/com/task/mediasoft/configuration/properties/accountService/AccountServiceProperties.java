package com.task.mediasoft.configuration.properties.accountService;

import com.task.mediasoft.configuration.properties.accountService.model.AccountMethodsProperties;
import com.task.mediasoft.configuration.properties.model.MockProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс для конфигурации свойств приложения, связанных с веб-сервисом для работы с аккаунтами.
 * Свойства задаются в файле application.yaml с префиксом "rest.account-service".
 */
@Configuration
@ConfigurationProperties(prefix = "rest.account-service")
@Getter
@Setter
public class AccountServiceProperties {

    /**
     * Модель настроек мок-сервиса.
     */
    private MockProperties mock;

    /**
     * Хост второго сервиса для работы с аккаунтами.
     */
    private String host;

    /**
     * Модель настроек методов для работы с аккаунтами.
     */
    private AccountMethodsProperties methods;
}