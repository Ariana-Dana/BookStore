package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.model.*;
import com.example.bookstore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderDTO createOrder(OrderDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);

        List<OrderItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    Book book = bookRepository.findById(itemDto.getBookId())
                            .orElseThrow(() -> new RuntimeException("Book not found"));
                    OrderItem item = new OrderItem();
                    item.setBook(book);
                    item.setQuantity(itemDto.getQuantity());
                    item.setPrice(book.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setItems(items);
        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);

        return mapToDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotal(order.getTotal());
        dto.setItems(order.getItems().stream()
                .map(item -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTO.setId(item.getId()); 
                    itemDTO.setBookId(item.getBook().getId());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(item.getPrice());
                    return itemDTO;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}
