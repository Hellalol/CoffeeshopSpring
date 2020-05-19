package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.Purchase;
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

    @GetMapping("/{id}/product")
    public List<Optional<Product>> getproduct(@PathVariable Long id) {

        //instansveriabler
        Optional<Product> selectedProduct;
        Long selectedProductId;
        List<PurchaseEntryDto> productDTOList;
        List<Optional<Product>> productList = new ArrayList<>();

        //låta annan metod hämta purchaseDto
        PurchaseDto purchaseDto = getPurchase(id);

        //ta ut PurchaseEntriesListan från purchaseDto
        productDTOList = purchaseDto.getPurchaseEntries();


        //hämta produktId från listan av purchesEntries
        for (int i = 0; i < productDTOList.size() ; i++) {
            selectedProductId = productDTOList.get(i).getProductId();
            //hämta varje produkt med produktId i purchesEntriesListan
            selectedProduct = productService.getById(selectedProductId);
            //om den hittar, lägg in i produktlista
            if (selectedProduct.isPresent()){
                productList.add(selectedProduct);
            }

        }

        //skicka hela produkt listan
        return productList;
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
}
