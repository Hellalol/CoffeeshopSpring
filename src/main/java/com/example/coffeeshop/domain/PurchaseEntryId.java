package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PurchaseEntryId.class)
public class PurchaseEntryId implements Serializable {
    @Id
    private Long purchase;

    @Id
    private Long product;
}
