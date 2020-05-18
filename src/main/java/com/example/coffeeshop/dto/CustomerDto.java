package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Purchase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Data //Förmodligen behövs inte den här
@RequiredArgsConstructor
public class CustomerDto {

    private final Long id;
    private final String name;
    private final String username;
    private final boolean premiumCustomer; //Gör om till string, ändra värdet beroende på true/false
    private List<Purchase> purchases;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.username = customer.getUsername();
        this.premiumCustomer = customer.isPremiumCustomer();
        this.purchases = customer.getPurchases();
    }
}