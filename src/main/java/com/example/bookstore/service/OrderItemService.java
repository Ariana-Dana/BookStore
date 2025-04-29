package com.example.bookstore.service;

import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository; 

    @Transactional
    public OrderItemDTO save(OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());

        bookRepository.findById(dto.getBookId()).ifPresent(orderItem::setBook);

        OrderItem saved = orderItemRepository.save(orderItem);

        return new OrderItemDTO(
                saved.getId(),
                saved.getQuantity(),
                saved.getPrice(),
                saved.getBook() != null ? saved.getBook().getId() : null
        );
    }

    public OrderItemDTO findById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItem -> new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getBook() != null ? orderItem.getBook().getId() : null
                ))
                .orElse(null);
    }

    public List<OrderItemDTO> findAll() {
        return orderItemRepository.findAll().stream()
                .map(orderItem -> new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getBook() != null ? orderItem.getBook().getId() : null
                ))
                .toList();
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}

