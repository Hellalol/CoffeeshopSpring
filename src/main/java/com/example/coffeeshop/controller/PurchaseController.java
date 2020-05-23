package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.dto.PurchaseDto;
import com.example.coffeeshop.dto.PurchaseEntryDto;
import com.example.coffeeshop.service.CustomerService;
import com.example.coffeeshop.service.ProductService;
import com.example.coffeeshop.service.PurchaseService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/purchase")
// TODO Throw correct exceptions
public class PurchaseController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PurchaseController.class);
    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, ProductService productService, CustomerService customerService) {
        this.purchaseService = purchaseService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO Replace input parameter with wherever we're actually getting the customer from
    public PurchaseDto create(Customer c) {
        return new PurchaseDto(purchaseService.getNewPurchase(c));
    }

    @PostMapping("/new2/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseDto create2(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id).orElseThrow(NoSuchElementException::new);
        return new PurchaseDto(purchaseService.getNewPurchase(customer));
    }

    @PostMapping("/{id}/checkout")
    public PurchaseDto checkout(@PathVariable long id) {
        Purchase p = purchaseService.getById(id).orElseThrow(NoSuchElementException::new);
        return new PurchaseDto(purchaseService.checkout(p));
    }

    @CrossOrigin()
    @GetMapping("/{id}")
    public PurchaseDto getPurchase(@PathVariable Long id) {
        Purchase p = purchaseService.getById(id).orElseThrow(NoSuchElementException::new);
        return new PurchaseDto(p);
    }


    @PostMapping("/{id}/add")
    // TODO Hash out how this and putAll are supposed to work, also figure out request mapping
    public PurchaseDto put(@PathVariable long id, @RequestBody PurchaseEntryDto entry) {
        // TODO Check id and purchase entry
        Purchase purchase = purchaseService.getById(id).orElseThrow(NoSuchElementException::new);
        Product product = productService.getById(entry.getProductId()).orElseThrow(NoSuchElementException::new);
        purchaseService.setProductQuantity(purchase, product, entry.getQuantity());
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{id}/addAll")
    // TODO Hash out how this and put are supposed to work, also figure out request mapping
    public PurchaseDto putAll(@PathVariable long id, @RequestBody Iterable<PurchaseEntryDto> entries) {
        Purchase purchase = purchaseService.getById(id).orElseThrow(NoSuchElementException::new);
        for (PurchaseEntryDto entry : entries) {
            put(id, entry);
        }
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{purchaseId}/remove/{productId}")
    public PurchaseDto remove(@PathVariable long purchaseId, @PathVariable long productId) {
        Purchase purchase = purchaseService.getById(purchaseId).orElseThrow(NoSuchElementException::new);
        Product product = productService.getById(productId).orElseThrow(NoSuchElementException::new);
        // TODO A remove() method might be more elegant
        return new PurchaseDto(purchaseService.setProductQuantity(purchase, product, 0));
    }

    @PostMapping("/{id}/removeAll")
    public PurchaseDto removeAll(@PathVariable long id) {
        return purchaseService.getById(id)
                .map(purchaseService::emptyCart)
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
        Purchase purchase = purchaseService.getById(id).orElseThrow(NoSuchElementException::new);
        Product product = productService.getById(productId).orElseThrow(NoSuchElementException::new);
        purchaseService.setProductQuantity(purchase, product, purchase.getEntry(product).getQuantity() + 1);
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{id}/subtractByOne/{productId}")
    public PurchaseDto subtractByOne(@PathVariable long id, @PathVariable Long productId) {
        Purchase purchase = purchaseService.getById(id).orElseThrow(NoSuchElementException::new);
        Product product = productService.getById(productId).orElseThrow(NoSuchElementException::new);
        purchaseService.setProductQuantity(purchase, product, purchase.getEntry(product).getQuantity() - 1);
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{id}/removeProduct/{productId}")
    public PurchaseDto removeProduct(@PathVariable long id, @PathVariable Long productId) {
        Purchase purchase = purchaseService.getById(id).orElseThrow();
        Product product = productService.getById(productId).orElseThrow(NoSuchElementException::new);
        purchaseService.setProductQuantity(purchase, product, 0);
        return new PurchaseDto(purchase);
    }

    @PostMapping("/{purchaseId}/addProductToPurches/{productId}")
    public PurchaseDto addProductToPurchase(@PathVariable Long purchaseId, @PathVariable Long productId) {
        //Metoden kallas efter nytt purchase ID har skapats
        Purchase purchase = purchaseService.getById(purchaseId).orElseThrow(NoSuchElementException::new);
        Product product = productService.getById(productId).orElseThrow(NoSuchElementException::new);
        purchaseService.setProductQuantity(purchase, product, purchase.getEntry(product).getQuantity() + 1);
        return new PurchaseDto(purchase);
    }
}
