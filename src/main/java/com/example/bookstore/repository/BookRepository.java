package com.example.bookstore.repository;

import org.springframework.stereotype.Repository;
import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable); 
    Page<Book> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);
    Page<Book> findByGenresNameContainingIgnoreCase(String genreName, Pageable pageable);
    Page<Book> findByPrice(BigDecimal price, Pageable pageable);

}
