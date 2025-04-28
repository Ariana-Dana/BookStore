package com.example.bookstore.service;

import com.example.bookstore.dto.GenreDTO;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<GenreDTO> findAll() {
        return genreRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public GenreDTO findById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return mapToDTO(genre);
    }

    public GenreDTO save(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());
        Genre saved = genreRepository.save(genre);
        return mapToDTO(saved);
    }

    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    private GenreDTO mapToDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
