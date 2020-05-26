package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.dto.CustomerDto;
import com.example.coffeeshop.dto.PurchaseDto;
import com.example.coffeeshop.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    @ResponseBody
    public Customer put(@RequestBody Customer newCustomer) {
        return customerService.registerCustomer(newCustomer);
    }

    @CrossOrigin()
    @GetMapping(path = "/all")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(CustomerDto::new)
                .collect(Collectors.toList());
    }

    // methods for getting order in admin page
    @CrossOrigin()
    @GetMapping(path = "/order/{id}")
    public List<PurchaseDto> getAllOrder(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .orElseThrow()
                .getPurchases().stream()
                .filter(purchase -> purchase.getStatus().equals(Purchase.Status.COMPLETED))
                .map(PurchaseDto::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin()
    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id).orElseThrow();
    }
}