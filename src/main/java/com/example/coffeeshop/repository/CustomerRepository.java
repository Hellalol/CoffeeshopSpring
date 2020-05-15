package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // TODO Some sort of registering

    Optional<Customer>findCustomerByName(String customerName);//TODO kontrollera om den här behövs verkligen
}
