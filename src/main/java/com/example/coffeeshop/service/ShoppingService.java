package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import com.example.coffeeshop.domain.PurchaseEntryId;
import com.example.coffeeshop.repository.PurchaseEntryRepository;
import com.example.coffeeshop.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// TODO Implement discount logic, here or elsewhere
public final class ShoppingService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseEntryRepository entryRepository;

    public ShoppingService(@Autowired PurchaseRepository purchaseRepository, @Autowired PurchaseEntryRepository entryRepository) {
        this.purchaseRepository = purchaseRepository;
        this.entryRepository = entryRepository;
    }

    public Purchase setQuantity(Purchase purchase, Product product, int quantity) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be edited");
        }
        PurchaseEntry entry = entryRepository
                .findById(new PurchaseEntryId(purchase.getId(), product.getId()))
                .orElse(new PurchaseEntry(purchase, product, 0, product.getBasePrice()));
        entry.setQuantity(quantity);
        purchase.getPurchaseEntries().put(product, entry);
        clearStaleEntries(purchase);
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

    private void clearStaleEntries(Purchase purchase) {
        // Since this is only used internally, there's no need to check purchase status within this method
        purchase.getPurchaseEntries().values().removeIf(purchaseEntry -> purchaseEntry.getQuantity() < 1);
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
