package com.task.mediasoft.customer.model.dto;

import com.task.mediasoft.customer.model.Customer;
import lombok.Data;

@Data
public class ViewCustomerDTO {
    private final Long id;
    private final String login;
    private final String email;
    private final Boolean isActive;

    public ViewCustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.login = customer.getLogin();
        this.email = customer.getEmail();
        this.isActive = customer.getIsActive();
    }
}
