package com.example.coffeeshop.repository;

import com.example.coffeeshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    // Springs repository-interfaces returnerar den klass som definierats i klassen
    // För att hämta (t.ex.) bara kunder måste vi antingen casta eller skriva egen query
}
