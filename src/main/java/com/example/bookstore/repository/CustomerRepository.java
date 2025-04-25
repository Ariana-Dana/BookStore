package com.example.bookstore.repository;

import com.example.bookstore.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer>findByEmail(String email);
}
