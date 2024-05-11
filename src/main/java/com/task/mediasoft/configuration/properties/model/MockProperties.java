package com.task.mediasoft.configuration.properties.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель настроек мок-сервиса.
 */
@Getter
@Setter
public class MockProperties {

    /**
     * Признак включения мок-сервиса.
     */
    private boolean enabled;
}