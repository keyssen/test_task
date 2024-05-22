package com.task.mediasoft.customer.model;

import com.task.mediasoft.customer.model.dto.SaveCustomerDTO;
import com.task.mediasoft.order.model.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * Сущность, представляющая клиента.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customer")
public class Customer {

    /**
     * Уникальный идентификатор клиента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * Логин клиента.
     */
    @NonNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    /**
     * Адрес электронной почты клиента.
     */
    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Флаг, указывающий, активен ли клиент.
     */
    @NonNull
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    /**
     * Заказы, сделанные клиентом.
     */
    @OneToMany(mappedBy = "customer") // Указываем OneToMany, указывая имя поля в связанной сущности
    private List<Order> orders;

    /**
     * Создает нового клиента на основе данных из DTO.
     *
     * @param saveCustomerDTO Объект SaveCustomerDTO с данными нового клиента.
     */
    public Customer(SaveCustomerDTO saveCustomerDTO) {
        this.login = saveCustomerDTO.getLogin();
        this.email = saveCustomerDTO.getEmail();
        this.isActive = saveCustomerDTO.getIsActive();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getId(), customer.getId()) && Objects.equals(getLogin(), customer.getLogin()) && Objects.equals(getEmail(), customer.getEmail()) && Objects.equals(getIsActive(), customer.getIsActive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getEmail(), getIsActive());
    }
}
