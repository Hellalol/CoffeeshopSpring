package com.example.coffeeshop.service;

import com.example.coffeeshop.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class PurchaseListingService {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseListingService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

//    public List<Purchase> getCustomerPurchases(Customer c) {
//        return purchaseRepository.findByCustomerOrderByPurchaseTimeDesc(c);
//    }
}
