package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.ToString;

import java.util.*;

@ToString
// TODO Adapt to REST statelessness
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

    public Customer getCustomer() {
        return workingCustomer;
    }

    public void increment(Product product) {
        PurchaseEntry entry = workingEntries.getOrDefault(product, new PurchaseEntry(workingPurchase, product, 0, product.getBasePrice()));
        entry.setQuantity(entry.getQuantity() + 1);
        workingEntries.put(product, entry);
    }

    public void decrement(Product product) {
        if (workingEntries.containsKey(product)) {
            PurchaseEntry entry = workingEntries.get(product);
            entry.setQuantity(Math.max(0, entry.getQuantity() - 1));
        }
    }

    public void zeroOut(Product product) {
        if (workingEntries.containsKey(product)) {
            PurchaseEntry entry = workingEntries.get(product);
            entry.setQuantity(0);
        }
    }

    public void clearStaleEntries() {
        workingEntries.entrySet().removeIf(mapEntry -> mapEntry.getValue().getQuantity() < 1);
    }

    public Purchase toPurchase() {
        clearStaleEntries();
        workingPurchase.setPurchaseEntries(new HashSet<>(workingEntries.values()));
        return workingPurchase;
    }

    public static void main(String[] args) {

    }
}
