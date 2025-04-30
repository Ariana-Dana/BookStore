package com.example.bookstore;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.GenreRepository;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBook() {
        BookDTO dto = new BookDTO();
        dto.setTitle("Clean Code");
        dto.setDescription("A book about clean code");
        dto.setPrice(BigDecimal.valueOf(99.99));
        dto.setAuthorId(1L);
        dto.setGenreIds(Arrays.asList(10L));

        Author author = new Author();
        author.setId(1L);
        author.setName("Robert C. Martin");

        Genre genre = new Genre();
        genre.setId(10L);
        genre.setName("Programming");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(genreRepository.findAllById(dto.getGenreIds())).thenReturn(List.of(genre));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(1L);
            return savedBook;
        });

        BookDTO result = bookService.save(dto);

        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
        assertEquals(1L, result.getAuthorId());
        assertEquals(List.of(10L), result.getGenreIds());
    }

    @Test
    void testFindById() {
        Author author = new Author();
        author.setId(2L);
        author.setName("Author Test");

        Book book = new Book();
        book.setId(100L);
        book.setTitle("Java Basics");
        book.setDescription("Intro to Java");
        book.setPrice(BigDecimal.TEN);
        book.setAuthor(author);

        when(bookRepository.findById(100L)).thenReturn(Optional.of(book));

        BookDTO dto = bookService.findById(100L);

        assertEquals(100L, dto.getId());
        assertEquals("Java Basics", dto.getTitle());
        assertEquals(2L, dto.getAuthorId());
    }

    @Test
    void testSearchBooksByTitle() {
        String title = "Spring";

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot in Action");

        Page<Book> page = new PageImpl<>(List.of(book));
        Pageable pageable = PageRequest.of(0, 10);

        when(bookRepository.findByTitleContainingIgnoreCase(title, pageable)).thenReturn(page);

        Page<BookDTO> result = bookService.searchBooks(title, null, null, null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Spring Boot in Action", result.getContent().get(0).getTitle());
    }
}