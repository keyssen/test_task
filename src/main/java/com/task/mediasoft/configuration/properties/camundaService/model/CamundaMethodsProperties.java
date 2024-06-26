package com.task.mediasoft.configuration.properties.camundaService.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель настроек методов для работы с сервисом аккаунтов.
 */
@Getter
@Setter
public class CamundaMethodsProperties {

    /**
     * URL эндпоинта для получения номеров аккаунта.
     */
    private String startConfirm;
}