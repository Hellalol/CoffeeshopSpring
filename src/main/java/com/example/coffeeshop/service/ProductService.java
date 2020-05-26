package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> search(String query) {
        return productRepository.findDistinctByProductNameContainingOrDescriptionContaining(query, query);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
