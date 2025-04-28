package com.example.bookstore.controller;

import com.example.bookstore.dto.GenreDTO;
import com.example.bookstore.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<GenreDTO> getAllGenres() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public GenreDTO getGenreById(@PathVariable Long id) {
        return genreService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        return genreService.save(genreDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
    }
}
