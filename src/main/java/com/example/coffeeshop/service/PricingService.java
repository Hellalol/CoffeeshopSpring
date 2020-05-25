package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {
    public BigDecimal calculate(Customer c, Product p) {
        BigDecimal priceFactor = c.isPremiumCustomer() ? BigDecimal.valueOf(0.9) : BigDecimal.ONE;
        return priceFactor.multiply(p.getBasePrice()).setScale(2, RoundingMode.UP);
    }
}
