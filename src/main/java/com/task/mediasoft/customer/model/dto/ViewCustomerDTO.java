package com.task.mediasoft.customer.model.dto;

import com.task.mediasoft.customer.model.Customer;
import lombok.Data;

/**
 * Data Transfer Object (DTO) для передачи данных о клиенте для отображения.
 */
@Data
public class ViewCustomerDTO {

    /**
     * Уникальный идентификатор клиента.
     */
    private final Long id;

    /**
     * Логин клиента.
     */
    private final String login;

    /**
     * Адрес электронной почты клиента.
     */
    private final String email;

    /**
     * Флаг, указывающий, активен ли клиент.
     */
    private final Boolean isActive;

    /**
     * Создает новый объект ViewCustomerDTO на основе сущности {@link Customer}.
     *
     * @param customer Объект Customer, из которого извлекаются данные для отображения.
     */
    public ViewCustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.login = customer.getLogin();
        this.email = customer.getEmail();
        this.isActive = customer.getIsActive();
    }
}
