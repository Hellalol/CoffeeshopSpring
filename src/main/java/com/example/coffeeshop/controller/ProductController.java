package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.dto.ProductDto;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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

    @GetMapping(path = "/showProductsBySearch/{search}")
    public List<ProductDto> showProducts(@PathVariable String search){

        List<ProductDto> returnList;


        returnList = productService.search(search).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
        log.info(returnList.toString());


        if(returnList.size()>0){
            return returnList;

        }else{
            returnList = getAllProducts();
            return returnList;
        }
    }
}
