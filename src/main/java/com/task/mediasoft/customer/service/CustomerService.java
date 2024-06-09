package com.task.mediasoft.customer.service;


import com.task.mediasoft.customer.exception.CustomerNotFoundExceptionById;
import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.customer.model.dto.SaveCustomerDTO;
import com.task.mediasoft.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с клиентами.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Получает клиента по его идентификатору.
     *
     * @param id Идентификатор клиента.
     * @return Объект Customer.
     * @throws CustomerNotFoundExceptionById Если клиент с указанным идентификатором не найден.
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundExceptionById(id));
    }

    /**
     * Получает список всех клиентов из базы данных.
     *
     * @return Список всех клиентов.
     */
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    /**
     * Создает нового клиента на основе переданных данных.
     *
     * @param createCustomerDTO Объект SaveCustomerDTO с данными нового клиента.
     * @return Созданный объект Customer.
     */
    @Transactional
    public Customer createCustomer(SaveCustomerDTO createCustomerDTO) {
        Customer customer = new Customer(createCustomerDTO);
        customer.setIsActive(true);
        return customerRepository.save(customer);
    }
}
