package com.task.mediasoft.customer.service;


import com.task.mediasoft.customer.exception.CustomerNotFoundExceptionById;
import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.customer.model.dto.SaveCustomerDTO;
import com.task.mediasoft.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundExceptionById(id));
    }


    @Transactional
    public Customer createCustomer(SaveCustomerDTO createCustomerDTO) {
        Customer customer = new Customer(createCustomerDTO);
        customer.setIsActive(true);
        return customerRepository.save(customer);
    }


//    @Transactional
//    public void deleteCustomer(UUID id) {
//        Customer currentCustomer = customerRepository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundExceptionById(id));
//        customerRepository.delete(currentCustomer);
//    }

    @Transactional
    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }
}
