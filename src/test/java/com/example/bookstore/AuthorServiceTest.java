package com.example.bookstore;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("J.K. Rowling");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("George R.R. Martin");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(2, result.size());
        assertEquals("J.K. Rowling", result.get(0).getName());
        assertEquals("George R.R. Martin", result.get(1).getName());
    }

    @Test
    void testFindById() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Agatha Christie");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.findById(1L);

        assertNotNull(result);
        assertEquals("Agatha Christie", result.getName());
    }

    @Test
    void testSave() {
        AuthorDTO dto = new AuthorDTO();
        dto.setName("Stephen King");

        Author saved = new Author();
        saved.setId(1L);
        saved.setName("Stephen King");

        when(authorRepository.save(any(Author.class))).thenReturn(saved);

        AuthorDTO result = authorService.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Stephen King", result.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(authorRepository).deleteById(1L);
        authorService.delete(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }
}