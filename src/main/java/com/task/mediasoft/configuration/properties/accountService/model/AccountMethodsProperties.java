package com.task.mediasoft.configuration.properties.accountService.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель настроек методов для работы с сервисом аккаунтов.
 */
@Getter
@Setter
public class AccountMethodsProperties {

    /**
     * URL эндпоинта для получения номеров аккаунта.
     */
    private String getAccount;
}