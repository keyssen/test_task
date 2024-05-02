package com.task.mediasoft.customer.repository;

import com.task.mediasoft.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
