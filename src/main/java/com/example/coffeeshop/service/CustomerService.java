package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(Customer customer) {
        Optional<Customer> checkCustomer = customerRepository.findCustomerByNameIgnoreCase(customer.getName());

        if (checkCustomer.isEmpty()) {
            Customer registeringCustomer = new Customer();
            registeringCustomer.setName(customer.getName());
            registeringCustomer.setPassword(customer.getPassword());
            registeringCustomer.setUsername(customer.getUsername());
            registeringCustomer.setActive(true);
            customerRepository.save(registeringCustomer);
            return registeringCustomer;
        } else {
            return customer;
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
}
