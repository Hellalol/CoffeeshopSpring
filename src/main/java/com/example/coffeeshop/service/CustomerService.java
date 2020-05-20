package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.dto.CustomerDto;
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


    public void registrateCustomer(String customerName, String password, String userName) {

        //Nödvändigt att kolla om kunden redan finns här? Bättre att göra på klientsidan?
        Optional<Customer> customer = customerRepository.findCustomerByNameIgnoreCase(customerName);
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

    public List<Customer>getAllCustomers(){
        return customerRepository.findAll();

    }
}
