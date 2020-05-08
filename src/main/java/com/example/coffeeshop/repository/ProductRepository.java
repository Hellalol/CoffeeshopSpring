package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    // Custom Product-queries goes here

    List<Product> findByBasePriceAndDescription(BigDecimal basePrice, String description);

}
