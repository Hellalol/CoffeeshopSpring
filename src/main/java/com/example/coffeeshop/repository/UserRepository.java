package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.Customer;
import com.example.coffeeshop.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findUserByUsername(@NotBlank String username);

}
