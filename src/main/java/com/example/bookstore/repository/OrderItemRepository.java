package com.example.bookstore.repository;

import com.example.bookstore.model.OrderItem;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
@Modifying
@Query("DELETE FROM OrderItem oi WHERE oi.book.id = :bookId")
void deleteByBookId(@Param("bookId") Long bookId);

}
