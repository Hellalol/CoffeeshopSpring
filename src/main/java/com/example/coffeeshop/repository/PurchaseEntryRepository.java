package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseEntryRepository extends JpaRepository<PurchaseEntry, Long> {
    Optional<PurchaseEntry> findFirstByPurchaseAndProduct(Purchase purchase, Product product);
}
