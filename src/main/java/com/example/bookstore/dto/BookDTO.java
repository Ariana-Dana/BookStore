package com.example.bookstore.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long authorId;
    private List<Long> genreIds;
}
