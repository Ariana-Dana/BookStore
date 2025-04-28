package com.example.bookstore.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Long customerId;
    private List<OrderItemDTO> items;
}
