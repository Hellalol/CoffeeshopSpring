package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.dto.CustomerDto;
import com.example.coffeeshop.dto.ProductDto;
import com.example.coffeeshop.dto.PurchaseDto;
import com.example.coffeeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

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

    @GetMapping(path = "/showProductsBySearch")
    public List<ProductDto> showProducts(String search){
        List<ProductDto> returnList;

        returnList = productService.search(search).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());

        if(returnList.size()>0){
            return returnList;

        }else{
            returnList = getAllProducts();
            return returnList;
        }
    }

    @GetMapping(path = "/showProducts")
    public List<ProductDto> showProducts(){

            return getAllProducts();
        }
}
