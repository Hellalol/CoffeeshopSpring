package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.PurchaseEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseEntryRepository extends JpaRepository<PurchaseEntry, Long> {
}
