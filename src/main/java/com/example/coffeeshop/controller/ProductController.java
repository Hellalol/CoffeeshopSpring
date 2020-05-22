package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.dto.ProductDto;
import com.example.coffeeshop.service.CustomerService;
import com.example.coffeeshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public ProductController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @CrossOrigin()
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id).orElseThrow();
    }

    @CrossOrigin()
    @GetMapping(path = "/all")
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/all/{customerId}")
    public List<ProductDto> getAllProductsCheckForPremium(@PathVariable Long customerId){
        Customer customer = customerService.getCustomerById(customerId).get();

        BigDecimal priceFactor = customer.isPremiumCustomer() ? new BigDecimal("0.9") : BigDecimal.ONE;
        return productService.getAllProducts().stream()
                .map(product -> new ProductDto(product, product.getBasePrice().multiply(priceFactor).setScale(2, RoundingMode.HALF_UP)))
                .collect(Collectors.toList());


    }

    @GetMapping(path = "/showProductsBySearch/{search}/{customerId}")
    public List<ProductDto> showProducts(@PathVariable String search, @PathVariable Long customerId){
        List<ProductDto> returnList;
        Customer customer = customerService.getCustomerById(customerId).get();
        BigDecimal priceFactor = customer.isPremiumCustomer() ? new BigDecimal("0.9") : BigDecimal.ONE;

        returnList = productService.search(search).stream()
                .map(product -> new ProductDto(product, product.getBasePrice().multiply(priceFactor).setScale(2, RoundingMode.HALF_UP)))
                .collect(Collectors.toList());
        if(returnList.size()>0){
            return returnList;

        }else{
            returnList = getAllProductsCheckForPremium(customerId);
            return returnList;
        }
    }
}
