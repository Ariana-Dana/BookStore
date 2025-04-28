package com.example.bookstore.dto;

import com.example.bookstore.model.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private Long bookId;

    public static OrderItem toEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        return orderItem;
    }

    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setBookId(orderItem.getBook() != null ? orderItem.getBook().getId() : null);
        return orderItemDTO;
    }

    public static List<OrderItemDTO> fromEntities(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
