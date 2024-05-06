package com.task.mediasoft.customer.repository;

import com.task.mediasoft.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Customer} в базе данных.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
