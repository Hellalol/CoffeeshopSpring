package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.dto.PurchaseDto;
import com.example.coffeeshop.dto.PurchaseEntryDto;
import com.example.coffeeshop.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
// TODO Throw correct exceptions
public class PurchaseController {
    private final ShoppingService shoppingService;

    @Autowired
    public PurchaseController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
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
    public PurchaseDto getPurchase(@PathVariable long id) {
        return shoppingService.getById(id).map(PurchaseDto::new).orElseThrow();
    }

    @PutMapping("/{id}/add")
    // TODO Hash out how this and putAll are supposed to work, also figure out request mapping
    public PurchaseDto put(@PathVariable long id, @RequestBody PurchaseEntryDto entry) {
        return null;
    }

    @PutMapping("/{id}/addAll")
    // TODO Hash out how this and put are supposed to work, also figure out request mapping
    public PurchaseDto putAll(@PathVariable long id, @RequestBody Iterable<PurchaseEntryDto> entries) {
        Purchase purchase = shoppingService.getById(id).orElseThrow();
        // Do the thing
        return null;
    }
}
