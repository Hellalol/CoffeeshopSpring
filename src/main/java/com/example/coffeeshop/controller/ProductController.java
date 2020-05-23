package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.dto.ProductDto;
import com.example.coffeeshop.service.CustomerService;
import com.example.coffeeshop.service.PricingService;
import com.example.coffeeshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final CustomerService customerService;
    private final PricingService pricingService;

    @Autowired
    public ProductController(ProductService productService, CustomerService customerService, PricingService pricingService) {
        this.productService = productService;
        this.customerService = customerService;
        this.pricingService = pricingService;
    }

    @CrossOrigin()
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id).orElseThrow();
    }

    @CrossOrigin()
    @GetMapping(path = "/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/all/{customerId}")
    public List<ProductDto> getAllProductsCheckForPremium(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId).orElseThrow();
        return productService.getAllProducts().stream()
                .map(product -> new ProductDto(product, pricingService.calculate(customer, product)))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/showProductsBySearch/{search}/{customerId}")
    public List<ProductDto> showProducts(@PathVariable String search, @PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId).orElseThrow();

        List<ProductDto> returnList = productService.search(search).stream()
                .map(product -> new ProductDto(product, pricingService.calculate(customer, product)))
                .collect(Collectors.toList());
        if (returnList.isEmpty()) {
            returnList = getAllProductsCheckForPremium(customerId);
        }
        return returnList;
    }
}
