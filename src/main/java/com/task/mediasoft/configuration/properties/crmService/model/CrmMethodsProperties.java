package com.task.mediasoft.configuration.properties.crmService.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель настроек методов для работы с CRM.
 */
@Getter
@Setter
public class CrmMethodsProperties {

    /**
     * URL эндпоинта для получения ИНН пользователей.
     */
    private String getInn;
}