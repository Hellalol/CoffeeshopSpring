package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import com.example.coffeeshop.repository.CustomerRepository;
import com.example.coffeeshop.repository.PurchaseEntryRepository;
import com.example.coffeeshop.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PurchaseService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);
    private final PurchaseRepository purchaseRepository;
    private final PurchaseEntryRepository entryRepository;
    private final PricingService pricingService;
    private final CustomerRepository customerRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseEntryRepository entryRepository, PricingService pricingService, CustomerRepository customerRepository) {
        this.purchaseRepository = purchaseRepository;
        this.entryRepository = entryRepository;
        this.pricingService = pricingService;
        this.customerRepository= customerRepository;
    }

    public Optional<Purchase> getById(long id) {
        return purchaseRepository.findById(id);
    }

    public Purchase emptyCart(Purchase p) {
        p.getPurchaseEntries().clear();
        return purchaseRepository.save(p);
    }

    public Purchase cancelPurchase(Purchase p) {
        if (p.getStatus() == Purchase.Status.COMPLETED) {
            throw new IllegalArgumentException("Completed purchases cannot be cancelled");
        } else if (p.getStatus() == Purchase.Status.IN_PROGRESS) {
            p.setStatus(Purchase.Status.CANCELLED);
            purchaseRepository.save(p);
        }
        // If the purchase is already cancelled, nothing happens
        return p;
    }

    public Purchase getNewPurchase(Customer c) {
        return purchaseRepository.save(new Purchase(c));
    }

    public Purchase incrementQuantity(Purchase purchase, Product product) {
        return setQuantity(purchase, product, getPurchaseEntry(purchase, product).getQuantity() + 1);
    }

    public Purchase decrementQuantity(Purchase purchase, Product product) {
        return setQuantity(purchase, product, Math.max(0, getPurchaseEntry(purchase, product).getQuantity() - 1));
    }

    public Purchase setQuantity(Purchase purchase, Product product, int quantity) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be edited");
        } else if (quantity < 0) {
            throw new IllegalArgumentException("Purchase quantities may not go below zero");
        }

        PurchaseEntry entry = getPurchaseEntry(purchase, product);
        entry.setQuantity(quantity);
        purchase.getPurchaseEntries().add(entry);
        clearStaleEntries(purchase);
        sanitizePrices(purchase);
        return purchaseRepository.save(purchase);
    }

    public Purchase removeProduct(Purchase purchase, Product product) {
        return setQuantity(purchase, product, 0);
    }

    private void clearStaleEntries(Purchase purchase) {
        // Since this is only used internally, there's no need to check purchase status within this method
        for (PurchaseEntry entry : purchase.getPurchaseEntries()) {
            if (entry.getQuantity() < 1) {
                entryRepository.delete(entry);
            }
        }
        purchase.getPurchaseEntries().removeIf(purchaseEntry -> purchaseEntry.getQuantity() < 1);
    }

    private void sanitizePrices(Purchase p) {
        p.getPurchaseEntries().forEach(entry -> entry.setCurrentPrice(pricingService.calculate(p.getCustomer(), entry.getProduct())));
    }

    public Purchase checkout(Purchase purchase) {
        if (purchase.getStatus() != Purchase.Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Only purchases in progress may be checked out");
        }
        clearStaleEntries(purchase);
        purchase.setStatus(Purchase.Status.COMPLETED);
        Purchase completedPurchase = purchaseRepository.save(purchase);
        updatePremiumStatus(purchase.getCustomer());
        customerRepository.save(purchase.getCustomer());
        return completedPurchase;
    }

    private void updatePremiumStatus(Customer customer) {
        // Once a customer's premium, it should stick; we don't want future
        // calculation changes to unexpectedly change current status
        if (!customer.isPremiumCustomer()) {
            customer.setPremiumCustomer(customer.getPurchases().stream()
                    .filter(purchase -> purchase.getStatus().equals(Purchase.Status.COMPLETED))
                    .map(Purchase::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .compareTo(BigDecimal.valueOf(500_000)) >= 0);
        }
    }

    private PurchaseEntry getPurchaseEntry(Purchase purchase, Product product) {
        return entryRepository.findFirstByPurchaseAndProduct(purchase, product)
                .orElseGet(() -> new PurchaseEntry(purchase, product));
    }
}
