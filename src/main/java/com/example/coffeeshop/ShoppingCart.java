package com.example.coffeeshop;

import com.example.coffeeshop.model.Customer;
import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.Purchase;
import com.example.coffeeshop.model.PurchaseEntry;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter // We do NOT want setters, and equals/hashmap should be superfluous
@ToString

// TODO Move to somewhere more appropriate
public class ShoppingCart {
    private final Customer workingCustomer;
    private final Map<Product, PurchaseEntry> workingEntries;
    private final Purchase workingPurchase;

    public ShoppingCart(Customer customer) {
        this.workingCustomer = customer;
        this.workingEntries = new TreeMap<>(Comparator.comparing(Product::getId));
        this.workingPurchase = new Purchase(workingCustomer);
    }

    public void increment(Product product) {
        PurchaseEntry entry = workingEntries.getOrDefault(product, new PurchaseEntry(workingPurchase, product, 0, product.getBasePrice()));
        entry.increment();
        workingEntries.put(product, entry);
    }

    public void decrement(Product product) {
        if (workingEntries.containsKey(product)) {
            PurchaseEntry entry = workingEntries.get(product);
            entry.decrement();
        }
    }

    public void setZero(Product product) {
        if (workingEntries.containsKey(product)) {
            PurchaseEntry entry = workingEntries.get(product);
            entry.setQuantity(0);
        }
    }

    public void clearStaleEntries() {
        workingEntries.entrySet().removeIf(mapEntry -> mapEntry.getValue().getQuantity() < 1);
    }

    public void checkout() {
        clearStaleEntries();
        workingPurchase.setPurchaseEntries(new HashSet<>(workingEntries.values()));
        // TODO Persist here or wherever checkout is called?
    }
}
