package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Purchase;
import com.example.coffeeshop.domain.PurchaseEntry;
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
    private final Long customerId;
    private final List<PurchaseEntryDto> purchaseEntries;
    private final BigDecimal totalPrice;
    private final String status;
    private final int totalQuantity;

    public PurchaseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.customerId = purchase.getCustomer().getId();
        this.purchaseEntries = purchase.getTruePurchaseEntries().stream()
                .map(PurchaseEntryDto::new)
                .sorted(Comparator.comparing(PurchaseEntryDto::getProductId))
                .collect(Collectors.toList());
        this.totalPrice = purchase.getTotalPrice();
        this.status = purchase.getStatus().name();
        this.totalQuantity = purchase.getTruePurchaseEntries().stream().mapToInt(PurchaseEntry::getQuantity).sum();
    }
}
