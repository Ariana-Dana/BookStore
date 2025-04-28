package com.example.bookstore.service;

import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItemDTO save(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = OrderItemDTO.toEntity(orderItemDTO);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return OrderItemDTO.fromEntity(savedOrderItem);
    }    

    public OrderItemDTO findById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem.map(OrderItemDTO::fromEntity).orElse(null);
    }

    public List<OrderItemDTO> findAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return OrderItemDTO.fromEntities(orderItems);
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }

    
}
