package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import com.example.coffeeshop.dto.PurchaseEntryDto;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@ToString
// TODO Adapt to REST statelessness
// TODO Move to somewhere more appropriate
// TODO Implement discount logic, here or elsewhere
public class ShoppingCart {
    @Getter
    private final Customer customer;
    private final Map<Product, PurchaseEntryDto> workingEntries;

    public ShoppingCart(Customer customer) {
        this.customer = customer;
        this.workingEntries = new TreeMap<>(Comparator.comparing(Product::getId));
    }

    public void increment(Product product) {
        PurchaseEntryDto entry = workingEntries.getOrDefault(product, new PurchaseEntryDto(product));
        entry = entry.withQuantity(entry.getQuantity() + 1);
        workingEntries.put(product, entry);
    }

    public void decrement(Product product) {
        if (workingEntries.containsKey(product)) {
            PurchaseEntryDto entry = workingEntries.get(product);
            workingEntries.put(product, entry.withQuantity(Math.max(0, entry.getQuantity() - 1)));
        }
    }

    public void zeroOut(Product product) {
        if (workingEntries.containsKey(product)) {
            workingEntries.put(product, workingEntries.get(product).withQuantity(0));
        }
    }

    public void clearStaleEntries() {
        workingEntries.values().removeIf(purchaseEntry -> purchaseEntry.getQuantity() < 1);
    }

    public Purchase toPurchase() {
        clearStaleEntries();
        Purchase purchase = new Purchase(customer);
        Set<PurchaseEntry> mappedEntries = workingEntries.entrySet().stream()
                .map(productAndEntry -> new PurchaseEntry(
                        purchase,
                        productAndEntry.getKey(),
                        productAndEntry.getValue().getQuantity(),
                        productAndEntry.getValue().getCurrentPrice()
                ))
                .collect(Collectors.toSet());
        purchase.setPurchaseEntries(mappedEntries);
        return purchase;
    }
}
