package com.task.mediasoft.customer.controller;

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

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ViewCustomerDTO getCustomerById(@PathVariable Long id) {
        return new ViewCustomerDTO(customerService.getCustomerById(id));
    }

    @PostMapping
    public ViewCustomerDTO createCustomer(@Valid @RequestBody SaveCustomerDTO customerDTO) {
        return new ViewCustomerDTO(customerService.createCustomer(customerDTO));
    }
}
