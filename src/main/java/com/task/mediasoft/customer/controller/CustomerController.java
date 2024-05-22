package com.task.mediasoft.customer.controller;

import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.customer.model.dto.SaveCustomerDTO;
import com.task.mediasoft.customer.model.dto.ViewCustomerDTO;
import com.task.mediasoft.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для работы с сущностью {@link Customer}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Получить информацию о клиенте по его идентификатору.
     *
     * @param id Идентификатор клиента.
     * @return Объект ViewCustomerDTO с информацией о клиенте.
     */
    @GetMapping("/{id}")
    public ViewCustomerDTO getCustomerById(@PathVariable Long id) {
        return new ViewCustomerDTO(customerService.getCustomerById(id));
    }

    /**
     * Создать нового клиента.
     *
     * @param customerDTO Объект SaveCustomerDTO с данными нового клиента.
     * @return Объект ViewCustomerDTO с информацией о созданном клиенте.
     */
    @PostMapping
    public ViewCustomerDTO createCustomer(@Valid @RequestBody SaveCustomerDTO customerDTO) {
        return new ViewCustomerDTO(customerService.createCustomer(customerDTO));
    }
}
