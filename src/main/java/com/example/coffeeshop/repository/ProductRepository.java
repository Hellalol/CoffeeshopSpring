package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findDistinctByProductNameContainingOrDescriptionContaining(String name, String desc);
}
