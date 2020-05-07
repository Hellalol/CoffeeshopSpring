package com.example.coffeeshop.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class PurchaseEntryId implements Serializable {
    private Long purchase;
    private Long product;
}
