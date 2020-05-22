package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.dto.CustomerDto;
import com.example.coffeeshop.dto.PurchaseDto;
import com.example.coffeeshop.repository.CustomerRepository;
import com.example.coffeeshop.service.CustomerService;
import com.example.coffeeshop.service.ProductService;
import com.example.coffeeshop.service.PurchaseListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    CustomerService customerService;
    PurchaseListingService purchaseListingService;
    ProductService productService;


    @Autowired
    public CustomerController(CustomerService customerService,
                              PurchaseListingService purchaseListingService,
                              ProductService productService) {
        this.customerService = customerService;
        this.purchaseListingService = purchaseListingService;
        this.productService = productService;
    }


    @PostMapping("/register")
    @ResponseBody
    public Customer put (@RequestBody Customer newCustomer) {
       return customerService.registrateCustomer(newCustomer);
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
        return customerService.getAllCustomers().stream()
                .filter(customer -> customer.getId().equals(id))
                .map(Customer::getPurchases)
                .flatMap(Collection::stream)
                .map(PurchaseDto::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin()
    @GetMapping(path = "/product/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @CrossOrigin()
    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id).get();
    }
}