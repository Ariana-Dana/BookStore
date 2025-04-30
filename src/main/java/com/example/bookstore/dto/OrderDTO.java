package com.example.bookstore.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    @NotNull
    private Long customerId;

    private LocalDateTime orderDate;

    private BigDecimal total;

    private List<OrderItemDTO> items;
}
