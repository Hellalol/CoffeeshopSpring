package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PurchaseEntryId implements Serializable {
    @Column(name = "purchase_id")
    private Long purchase;

    @Column(name = "product_id")
    private Long product;
}
