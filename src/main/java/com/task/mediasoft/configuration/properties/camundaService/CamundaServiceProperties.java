package com.task.mediasoft.configuration.properties.camundaService;

import com.task.mediasoft.configuration.properties.camundaService.model.CamundaMethodsProperties;
import com.task.mediasoft.configuration.properties.model.MockProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rest.camunda-service")
@Getter
@Setter
public class CamundaServiceProperties {

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
    private CamundaMethodsProperties methods;
}