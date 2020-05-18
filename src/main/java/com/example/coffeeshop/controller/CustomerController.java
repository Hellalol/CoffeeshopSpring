package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.repository.CustomerRepository;
import com.example.coffeeshop.repository.UserRepository;
import com.example.coffeeshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    CustomerRepository customerRepository;
    UserRepository userRepository;
    CustomerService customerService;

    @Autowired
    public CustomerController(UserRepository userRepository, CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.userRepository = userRepository;
    }

    //TODO : Om klienten skriver in en mailadress som redan finns crashar programmet. Åtgärd?
    @PostMapping()
    Customer customer (@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    @GetMapping(path = "/all")
    List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}