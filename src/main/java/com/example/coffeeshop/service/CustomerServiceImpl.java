package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{


    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void registrateCustomer(String customerName, String password, String userName) {

        //Nödvändigt att kolla om kunden redan finns här? Bättre att göra på klientsidan?
        Optional<Customer> customer = customerRepository.findCustomerByName(customerName);
        Customer registratingCustomer = new Customer();

        if(!customer.isPresent()){ //Om kunden inte hittas från databasen sätts fieldsen i if-satsen.
            registratingCustomer.setName(customerName);
            registratingCustomer.setPassword(password);
            registratingCustomer.setUsername(userName);
        }
        else
            //log -> return "Customer already exists in the world";
                //KOLLA AMIGOSCODE


        //Kunden sparas ner i databasen med repot.
        customerRepository.save(registratingCustomer);


         //log -> return "Customer registration completed.";
    }
}
