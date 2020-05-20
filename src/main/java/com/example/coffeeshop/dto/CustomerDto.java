package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Purchase;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data //Förmodligen behövs inte den här
@RequiredArgsConstructor
public class CustomerDto {

    private final Long id;
    private final String name;
    private final String username;
    private final String premiumCustomer;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.username = customer.getUsername();
        this.premiumCustomer = customer.isPremiumCustomer() ? "Premium" : "Regular";
    }

}