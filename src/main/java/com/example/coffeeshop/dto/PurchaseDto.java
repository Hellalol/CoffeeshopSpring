package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Purchase;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class PurchaseDto {
    private final Long id;
    private final Customer customer; // TODO Change to id or DTO
    private final List<PurchaseEntryDto> purchaseEntries;
    private final BigDecimal totalPrice;

    public PurchaseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.customer = purchase.getCustomer();
        this.purchaseEntries = purchase.getPurchaseEntries().values().stream()
                .map(PurchaseEntryDto::new)
                .sorted(Comparator.comparing(entry -> entry.getProduct().getId()))
                .collect(Collectors.toList());
        this.totalPrice = purchase.getTotalPrice();
    }
}
