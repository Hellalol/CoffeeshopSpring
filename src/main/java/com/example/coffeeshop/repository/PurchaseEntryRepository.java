package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import com.example.coffeeshop.domain.PurchaseEntryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseEntryRepository extends JpaRepository<PurchaseEntry, PurchaseEntryId> {
    List<PurchaseEntry> findByPurchase(Purchase purchase);
}
