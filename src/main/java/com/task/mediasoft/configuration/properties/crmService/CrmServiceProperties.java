package com.task.mediasoft.configuration.properties.crmService;


import com.task.mediasoft.configuration.properties.crmService.model.CrmMethodsProperties;
import com.task.mediasoft.configuration.properties.model.MockProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс для конфигурации свойств приложения, связанных с CRM веб-сервисом.
 * Свойства задаются в файле application.yaml с префиксом "rest.crm-service".
 */
@Configuration
@ConfigurationProperties(prefix = "rest.crm-service")
@Getter
@Setter
public class CrmServiceProperties {

    /**
     * Модель настроек мок-сервиса.
     */
    private MockProperties mock;

    /**
     * Хост второго сервиса для работы с CRM.
     */
    private String host;

    /**
     * Модель настроек методов для работы с CRM.
     */
    private CrmMethodsProperties methods;
}