package com.task.mediasoft.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.lang.Nullable;

/**
 * Перечисление, представляющее поддерживаемые валюты.
 */
public enum CurrencyEnum {
    RUB("RUB"),
    CNY("CNY"),
    USD("USD"),
    EUR("EUR");

    /**
     * Код валюты.
     */
    private final String code;

    /**
     * Конструктор перечисления CurrencyEnum.
     *
     * @param code Код валюты.
     */
    CurrencyEnum(String code) {
        this.code = code;
    }

    /**
     * Получает код валюты.
     *
     * @return Код валюты.
     */
    @JsonValue
    public String getCode() {
        return code;
    }

    /**
     * Создает экземпляр перечисления CurrencyEnum на основе переданного кода.
     *
     * @param code Код валюты.
     * @return Экземпляр CurrencyEnum или null, если переданный код не соответствует ни одному элементу перечисления.
     */
    @JsonCreator
    public static @Nullable CurrencyEnum fromCode(String code) {
        for (CurrencyEnum currency : CurrencyEnum.values()) {
            if (currency.name().equals(code) || currency.code.equals(code)) {
                return currency;
            }
        }
        return null;
    }
}