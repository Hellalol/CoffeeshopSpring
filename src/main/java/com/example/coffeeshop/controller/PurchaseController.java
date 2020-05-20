package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
import com.example.coffeeshop.dto.CustomerDto;
import com.example.coffeeshop.dto.PurchaseDto;
import com.example.coffeeshop.dto.PurchaseEntryDto;
import com.example.coffeeshop.service.ProductService;
import com.example.coffeeshop.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchase")
// TODO Throw correct exceptions
public class PurchaseController {
    private final ShoppingService shoppingService;
    private final ProductService productService;

    @Autowired
    public PurchaseController(ShoppingService shoppingService, ProductService productService) {
        this.shoppingService = shoppingService;
        this.productService = productService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO Replace input parameter with wherever we're actually getting the customer from
    public PurchaseDto create(Customer c) {
        return new PurchaseDto(shoppingService.getNewPurchase(c));
    }

    @PostMapping("/{id}/checkout")
    public PurchaseDto checkout(@PathVariable long id) {
        Purchase p = shoppingService.getById(id).orElseThrow();
        return new PurchaseDto(shoppingService.checkout(p));
    }

    @GetMapping("/{id}")
    public PurchaseDto getPurchase(@PathVariable Long id) {
        Purchase p = shoppingService.getById(id).orElseThrow();
        return new PurchaseDto(p);
    }


    @PostMapping("/{id}/add")
    // TODO Hash out how this and putAll are supposed to work, also figure out request mapping
    public PurchaseDto put(@PathVariable long id, @RequestBody PurchaseEntryDto entry) {
        // TODO Check id and purchase entry
        Purchase purchase = shoppingService.getById(id).orElseThrow();
        Product product = productService.getById(entry.getProductId()).orElseThrow();
        return new PurchaseDto(shoppingService.setProductQuantity(purchase, product, entry.getQuantity()));
    }

    @PostMapping("/{id}/addAll")
    // TODO Hash out how this and put are supposed to work, also figure out request mapping
    public PurchaseDto putAll(@PathVariable long id, @RequestBody Iterable<PurchaseEntryDto> entries) {
        Purchase purchase = shoppingService.getById(id).orElseThrow();
        for (PurchaseEntryDto entry : entries) {
            put(id, entry);
        }
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{purchaseId}/remove/{productId}")
    public PurchaseDto remove(@PathVariable long purchaseId, @PathVariable long productId) {
        Purchase purchase = shoppingService.getById(purchaseId).orElseThrow();
        Product product = productService.getById(productId).orElseThrow();
        // TODO A remove() method might be more elegant
        return new PurchaseDto(shoppingService.setProductQuantity(purchase, product, 0));
    }

    @PostMapping("/{id}/removeAll")
    public PurchaseDto removeAll(@PathVariable long id) {
        return shoppingService.getById(id)
                .map(shoppingService::emptyCart)
                .map(PurchaseDto::new)
                .orElseThrow();
    }

    @PostMapping("/{id}/replace")
    public PurchaseDto replace(@PathVariable long id, PurchaseDto newPurchase) {
        removeAll(id);
        return putAll(id, newPurchase.getPurchaseEntries());
    }


    @PostMapping("/{id}/addByOne/{productId}")
    public PurchaseDto addByOne(@PathVariable long id, @PathVariable Long productId) {
        Optional<Product> product = productService.getById(productId);
        Purchase purchase = shoppingService.getById(id).orElseThrow();
        Product realProduct = purchase.getEntry(product.get()).getProduct();
        int newQuantity = purchase.getEntry(product.get()).getQuantity()+1;
        shoppingService.setProductQuantity(purchase, realProduct, newQuantity);
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{id}/subtractByOne/{productId}")
    public PurchaseDto subtractByOne(@PathVariable long id, @PathVariable Long productId) {
        Optional<Product> product = productService.getById(productId);
        Purchase purchase = shoppingService.getById(id).orElseThrow();
        Product realProduct = purchase.getEntry(product.get()).getProduct();
        int newQuantity = purchase.getEntry(product.get()).getQuantity()-1;
        shoppingService.setProductQuantity(purchase, realProduct, newQuantity);
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{id}/removeProduct/{productId}")
    public PurchaseDto removeProduct(@PathVariable long id, @PathVariable Long productId) {
        Optional<Product> product = productService.getById(productId);
        Purchase purchase = shoppingService.getById(id).orElseThrow();
        Product realProduct = purchase.getEntry(product.get()).getProduct();
        shoppingService.setProductQuantity(purchase, realProduct, 0);
        return new PurchaseDto(purchase);
    }

}
