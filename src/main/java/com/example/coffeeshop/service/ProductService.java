package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> search(String query) {
        return productRepository.findDistinctByProductNameContainingOrDescriptionContaining(query, query);
    }
}
