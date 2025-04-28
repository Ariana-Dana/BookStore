package com.example.bookstore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}
