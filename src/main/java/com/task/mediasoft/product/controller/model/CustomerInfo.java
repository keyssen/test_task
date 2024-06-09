package com.task.mediasoft.product.controller.model;

import lombok.Data;

/**
 * Класс, представляющий информацию о клиенте.
 */
@Data
public class CustomerInfo {

    /**
     * Уникальный идентификатор клиента.
     */
    final Long id;

    /**
     * Номер счета клиента.
     */
    final String accountNumber;

    /**
     * Электронная почта клиента.
     */
    final String email;

    /**
     * ИНН клиента.
     */
    final String inn;
}