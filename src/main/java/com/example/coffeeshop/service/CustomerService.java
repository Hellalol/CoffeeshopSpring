package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.User;

import java.util.List;


// TODO : Validated?
public interface CustomerService {
    void registrateCustomer(String customerName, String password, String userName);
}
