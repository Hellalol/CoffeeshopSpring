package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import com.example.coffeeshop.domain.PurchaseEntryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseEntryRepository extends JpaRepository<PurchaseEntry, PurchaseEntryId> {
    List<PurchaseEntry> findByPurchase(Purchase purchase);
}
