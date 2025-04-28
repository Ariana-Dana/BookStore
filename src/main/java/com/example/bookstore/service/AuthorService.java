package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return mapToDTO(author);
    }

    public AuthorDTO save(AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        Author saved = authorRepository.save(author);
        return mapToDTO(saved);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    private AuthorDTO mapToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        return dto;
    }
}
