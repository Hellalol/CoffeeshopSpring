package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.dto.CustomerDto;
import com.example.coffeeshop.repository.CustomerRepository;
import com.example.coffeeshop.repository.UserRepository;
import com.example.coffeeshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/customer")
public class CustomerController {

    CustomerRepository customerRepository;
    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    //TODO : Om klienten skriver in en mailadress som redan finns crashar programmet. Åtgärd?
    @PostMapping()
    public Customer customer (@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    @CrossOrigin()
    @GetMapping(path = "/all")
    public List<CustomerDto> getAllCustomers(){
        return customerService.getAllCustomers().stream()
                .map(CustomerDto::new)
                .collect(Collectors.toList());
    }
}