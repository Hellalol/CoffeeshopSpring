package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.*;
import com.example.coffeeshop.repository.PurchaseEntryRepository;
import com.example.coffeeshop.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
// TODO Implement discount logic, here or elsewhere
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

    public Purchase setQuantity(Purchase purchase, Product product, int quantity) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be edited");
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

    public Purchase increment(Purchase purchase, Product product) {
        if (purchase.getPurchaseEntries().containsKey(product)) {
            return this.setQuantity(purchase, product, purchase.getEntry(product).getQuantity() + 1);
        } else {
            return this.setQuantity(purchase, product, 1);
        }
    }

    public Purchase decrement(Purchase purchase, Product product) {
        // TODO Check behaviour on product not found
        if (purchase.getPurchaseEntries().containsKey(product)) {
            return this.setQuantity(purchase, product, purchase.getEntry(product).getQuantity() - 1);
        } else {
            return purchase;
        }
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
        BigDecimal priceFactor = c.isPremiumCustomer() ? BigDecimal.valueOf(0.9) : BigDecimal.ONE;
        return p.getBasePrice().multiply(priceFactor);
    }

    public Purchase checkout(Purchase purchase) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be checked out");
        }
        clearStaleEntries(purchase);
        purchase.setStatus(Purchase.Status.FINISHED);
        return purchaseRepository.save(purchase);
    }
}
