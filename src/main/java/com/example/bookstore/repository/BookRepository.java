package com.example.bookstore.repository;

import org.springframework.stereotype.Repository;
import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitle(String title, Pageable pageable);
    Page<Book> findByAuthor(String authorName, Pageable pageable);
    Page<Book> findByGenre(String genreName, Pageable pageable);
    Page<Book> findByPrice(Double price, Pageable pageable);
}
