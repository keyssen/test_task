package com.task.mediasoft.customer.model;

import com.task.mediasoft.customer.model.dto.SaveCustomerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NonNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @NonNull
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    public Customer(SaveCustomerDTO saveCustomerDTO) {
        this.login = saveCustomerDTO.getLogin();
        this.email = saveCustomerDTO.getEmail();
        this.isActive = saveCustomerDTO.getIsActive();
    }
}
