package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.repository.CustomerRepository;
import com.example.coffeeshop.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //TODO : Om klienten skriver in en mailadress som redan finns crashar programmet. Åtgärd?
    @PostMapping()
    Customer customer (@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}