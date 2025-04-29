package com.example.bookstore;

import com.example.bookstore.dto.GenreDTO;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import com.example.bookstore.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fantasy");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Sci-Fi");

        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        List<GenreDTO> result = genreService.findAll();

        assertEquals(2, result.size());
        assertEquals("Fantasy", result.get(0).getName());
        assertEquals("Sci-Fi", result.get(1).getName());
    }

    @Test
    void testFindById() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Mystery");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        GenreDTO result = genreService.findById(1L);

        assertNotNull(result);
        assertEquals("Mystery", result.getName());
    }

    @Test
    void testSave() {
        GenreDTO dto = new GenreDTO();
        dto.setName("Horror");

        Genre saved = new Genre();
        saved.setId(1L);
        saved.setName("Horror");

        when(genreRepository.save(any(Genre.class))).thenReturn(saved);

        GenreDTO result = genreService.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Horror", result.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(genreRepository).deleteById(1L);
        genreService.delete(1L);
        verify(genreRepository, times(1)).deleteById(1L);
    }
}