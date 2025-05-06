package com.example.bookstore.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long authorId;
    private String authorName;
    private List<Long> genreIds;
    private List<String> genreNames;
    private String genreNamesRaw;


    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setGenreNamesRaw(String raw) {
        this.genreNamesRaw = raw;
        this.genreNames = Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public String getGenreNamesRaw() {
        return genreNamesRaw;
    }

    
}
