package com.example.coffeeshop.domain;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PurchaseEntryId implements Serializable {
    private Long purchase;
    private Long product;
}
