package com.example.coffeeshop.service;

import com.example.coffeeshop.controller.PurchaseController;
import com.example.coffeeshop.domain.*;
import com.example.coffeeshop.repository.PurchaseEntryRepository;
import com.example.coffeeshop.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public final class ShoppingService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseEntryRepository entryRepository;
    private static final Logger log = LoggerFactory.getLogger(ShoppingService.class);

    @Autowired
    public ShoppingService(PurchaseRepository purchaseRepository, PurchaseEntryRepository entryRepository) {
        this.purchaseRepository = purchaseRepository;
        this.entryRepository = entryRepository;
    }

    public List<Purchase> getAllCustomers(){
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> getById(long id) {
        return purchaseRepository.findById(id);
    }

    public Purchase emptyCart(Purchase p) {
        p.getTruePurchaseEntries().clear();
        return purchaseRepository.save(p);
    }

    public Purchase cancelPurchase(Purchase p) {
        if (p.getStatus() == Purchase.Status.COMPLETED) {
            throw new IllegalArgumentException("Completed purchases cannot be cancelled");
        } else {
            // TODO Check for already cancelled purchase
            p.setStatus(Purchase.Status.CANCELLED);
            return purchaseRepository.save(p);
        }
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
        PurchaseEntry entry = purchase.getTruePurchaseEntries().stream().filter(e -> e.getProduct().equals(product)).findFirst()
                .orElse(new PurchaseEntry(purchase, product, 0, calculateCurrentPrice(purchase.getCustomer(), product)));
        entry.setQuantity(quantity);
        purchase.getTruePurchaseEntries().add(entry);
        clearStaleEntries(purchase);
        sanitizePrices(purchase);
        return purchaseRepository.save(purchase);
    }

    // TODO Sanitize purchase entries here or in the entity class?
    private void clearStaleEntries(Purchase purchase) {
        // Since this is only used internally, there's no need to check purchase status within this method
        for (PurchaseEntry entry : purchase.getTruePurchaseEntries()) {
            if (entry.getQuantity() < 1) {
                entryRepository.delete(entry);
            }
        }
        purchase.getTruePurchaseEntries().removeIf(purchaseEntry -> purchaseEntry.getQuantity() < 1);
    }

    // TODO This is a horrible hack
    private void sanitizePrices(Purchase p) {
        p.getTruePurchaseEntries().forEach(entry -> entry.setCurrentPrice(calculateCurrentPrice(p.getCustomer(), entry.getProduct())));
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

    public Purchase addNewProduct(Purchase purchase, Product product){
        //PurchaseEntry purchaseEntry = new PurchaseEntry(purchase, product, 1, product.getBasePrice());
        //entryRepository.save(purchaseEntry);
        //return purchaseRepository.save(purchase);
        return setProductQuantity(purchase, product, 1);
    }
}
