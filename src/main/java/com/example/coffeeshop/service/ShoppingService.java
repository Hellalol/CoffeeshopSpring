package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.*;
import com.example.coffeeshop.repository.PurchaseEntryRepository;
import com.example.coffeeshop.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
// TODO Migrate to DTOs? Might be simpler, might be the controllers' responsibility
public final class ShoppingService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseEntryRepository entryRepository;

    @Autowired
    public ShoppingService(PurchaseRepository purchaseRepository, PurchaseEntryRepository entryRepository) {
        this.purchaseRepository = purchaseRepository;
        this.entryRepository = entryRepository;
    }

    public Purchase getNewPurchase(Customer c) {
        return purchaseRepository.save(new Purchase(c));
    }

    public void deletePurchase(Purchase p) {
        if (p.getStatus() == Purchase.Status.COMPLETED) {
            throw new IllegalArgumentException("Completed purchases may not be deleted");
        } else {
            purchaseRepository.delete(p);
        }
    }

    public Purchase setProductQuantity(Purchase purchase, Product product, int quantity) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be edited");
        } else if (quantity < 0) {
            throw new IllegalArgumentException("Purchase quantities may not go below zero");
        }
        PurchaseEntry entry = entryRepository
                .findById(new PurchaseEntryId(purchase.getId(), product.getId()))
                .orElse(new PurchaseEntry(purchase, product, 0, calculateCurrentPrice(purchase.getCustomer(), product)));
        entry.setQuantity(quantity);
        purchase.getPurchaseEntries().put(product, entry);
        clearStaleEntries(purchase);
        sanitizePrices(purchase);
        return purchaseRepository.save(purchase);
    }

    // TODO Sanitize purchase entries here or in the entity class?
    private void clearStaleEntries(Purchase purchase) {
        // Since this is only used internally, there's no need to check purchase status within this method
        purchase.getPurchaseEntries().values().removeIf(purchaseEntry -> purchaseEntry.getQuantity() < 1);
    }

    // TODO This is a horrible hack
    private void sanitizePrices(Purchase p) {
        p.getPurchaseEntries().values().forEach(entry -> entry.setCurrentPrice(calculateCurrentPrice(p.getCustomer(), entry.getProduct())));
    }

    // TODO What if calculation changes during a long-lived purchase?
    private BigDecimal calculateCurrentPrice(Customer c, Product p) {
        BigDecimal priceFactor = c.isPremiumCustomer() ? new BigDecimal("0.9") : BigDecimal.ONE;
        return p.getBasePrice().multiply(priceFactor).setScale(2, RoundingMode.HALF_UP);
    }

    public Purchase checkout(Purchase purchase) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be checked out");
        }
        clearStaleEntries(purchase);
        purchase.setStatus(Purchase.Status.COMPLETED);
        return purchaseRepository.save(purchase);
    }
}
