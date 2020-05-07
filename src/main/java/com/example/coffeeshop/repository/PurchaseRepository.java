package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Customer;
import com.example.coffeeshop.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByCustomerOrderByPurchaseTime(Customer c);
}
