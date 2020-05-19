package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Purchase;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class PurchaseDto {
    private Long id;
    private Long customerId;
    private List<PurchaseEntryDto> purchaseEntries;
    private BigDecimal totalPrice;
    private String status;

    public PurchaseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.customerId = purchase.getCustomer().getId();
        this.purchaseEntries = purchase.getPurchaseEntries().values().stream()
               .map(PurchaseEntryDto::new)
               .sorted(Comparator.comparing(PurchaseEntryDto::getProductId))
               .collect(Collectors.toList());
        //this.purchaseEntries=new ArrayList<>();
        this.totalPrice = purchase.getTotalPrice();
        this.status = purchase.getStatus().name();
    }
}
