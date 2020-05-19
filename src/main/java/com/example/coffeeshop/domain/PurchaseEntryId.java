package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.Column;
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
    @Column(name = "purchase_id")
    private Long purchase;

    @Id
    @Column(name = "product_id")
    private Long product;

}
