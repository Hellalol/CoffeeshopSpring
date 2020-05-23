package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.*;
import com.example.coffeeshop.repository.PurchaseEntryRepository;
import com.example.coffeeshop.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class PurchaseService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);
    private final PurchaseRepository purchaseRepository;
    private final PurchaseEntryRepository entryRepository;
    private final PricingService pricingService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseEntryRepository entryRepository, PricingService pricingService) {
        this.purchaseRepository = purchaseRepository;
        this.entryRepository = entryRepository;
        this.pricingService = pricingService;
    }

    public List<Purchase> getAllPurchases() {
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

    public Purchase incrementProductQuantity(Purchase purchase, Product product) {
        return setProductQuantity(purchase, product, getPurchaseEntry(purchase, product).getQuantity() + 1);
    }

    public Purchase decrementProductQuantity(Purchase purchase, Product product) {
        return setProductQuantity(purchase, product, Math.max(0, getPurchaseEntry(purchase, product).getQuantity() - 1));
    }

    public Purchase setProductQuantity(Purchase purchase, Product product, int quantity) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be edited");
        } else if (quantity < 0) {
            throw new IllegalArgumentException("Purchase quantities may not go below zero");
        }

        PurchaseEntry entry = getPurchaseEntry(purchase, product);
        entry.setQuantity(quantity);
        purchase.getTruePurchaseEntries().add(entry);
        clearStaleEntries(purchase);
        sanitizePrices(purchase);
        return purchaseRepository.save(purchase);
    }

    public Purchase removeProduct(Purchase purchase, Product product) {
        return setProductQuantity(purchase, product, 0);
    }

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
        p.getTruePurchaseEntries().forEach(entry -> entry.setCurrentPrice(pricingService.calculate(p.getCustomer(), entry.getProduct())));
    }

    public Purchase checkout(Purchase purchase) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be checked out");
        }
        clearStaleEntries(purchase);
        purchase.setStatus(Purchase.Status.COMPLETED);
        // There used to be premium customer logic here. It's in the Customer class now.
        return purchaseRepository.save(purchase);
    }

    public Purchase addNewProduct(Purchase purchase, Product product) {
        return setProductQuantity(purchase, product, 1);
    }

    private PurchaseEntry getPurchaseEntry(Purchase purchase, Product product) {
        return entryRepository.findFirstByPurchaseAndProduct(purchase, product)
                .orElseGet(() -> new PurchaseEntry(purchase, product));
    }
}
