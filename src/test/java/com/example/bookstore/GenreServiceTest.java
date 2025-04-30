package com.example.bookstore;

import com.example.bookstore.dto.GenreDTO;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import com.example.bookstore.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fantasy");

        when(genreRepository.findAll()).thenReturn(List.of(genre));

        List<GenreDTO> result = genreService.findAll();

        assertEquals(1, result.size());
        assertEquals("Fantasy", result.get(0).getName());
    }

    @Test
    void testFindById() {
        Genre genre = new Genre();
        genre.setId(2L);
        genre.setName("Science Fiction");

        when(genreRepository.findById(2L)).thenReturn(Optional.of(genre));

        GenreDTO dto = genreService.findById(2L);

        assertEquals(2L, dto.getId());
        assertEquals("Science Fiction", dto.getName());
    }

    @Test
    void testSave() {
        GenreDTO dto = new GenreDTO();
        dto.setName("Mystery");

        Genre genre = new Genre();
        genre.setId(3L);
        genre.setName("Mystery");

        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        GenreDTO result = genreService.save(dto);

        assertEquals(3L, result.getId());
        assertEquals("Mystery", result.getName());
    }

    @Test
    void testDelete() {
        Long id = 4L;

        genreService.delete(id);

        verify(genreRepository, times(1)).deleteById(id);
    }
}