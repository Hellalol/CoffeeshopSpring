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


    public Customer registrateCustomer(Customer customer) {
        Optional<Customer> checkCustomer = customerRepository.findCustomerByNameIgnoreCase(customer.getName());

        if(checkCustomer.isEmpty()){
            Customer registratingCustomer = new Customer();
            registratingCustomer.setName(customer.getName());
            registratingCustomer.setPassword(customer.getPassword());
            registratingCustomer.setUsername(customer.getUsername());
            registratingCustomer.setActive(true);
            customerRepository.save(registratingCustomer);
            return registratingCustomer;
        }
        else
            return customer;
    }

    public List<Customer>getAllCustomers(){
        return customerRepository.findAll();

    }

    public Optional<Customer> getCustomerById(Long id){
        return customerRepository.findById(id);
    }
}
