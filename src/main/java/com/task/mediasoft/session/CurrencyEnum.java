package com.task.mediasoft.session;

public enum CurrencyEnum {
    RUB,
    CNY,
    USD,
    EUR;

    public static boolean equalsEnum(String currency) {
        for (CurrencyEnum operationType : CurrencyEnum.values()) {
            if (operationType.name().equals(currency)) {
                return true;
            }
        }
        return false;
    }
}