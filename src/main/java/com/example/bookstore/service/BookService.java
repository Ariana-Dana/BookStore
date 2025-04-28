package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::mapToDTO);
    }

    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return mapToDTO(book);
    }

    public BookDTO save(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        book.setAuthor(author);

        if (dto.getGenreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(dto.getGenreIds());
            book.setGenres(genres);
        }

        Book saved = bookRepository.save(book);
        return mapToDTO(saved);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());
        dto.setAuthorId(book.getAuthor() != null ? book.getAuthor().getId() : null);
        dto.setGenreIds(book.getGenres() != null ? book.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toList()) : null);
        return dto;
    }

    public Page<BookDTO> searchBooks(String title, String authorName, String genreName, BigDecimal price, Pageable pageable) {
    if (title != null) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable).map(this::mapToDTO);
    } else if (authorName != null) {
        return bookRepository.findByAuthorNameContainingIgnoreCase(authorName, pageable).map(this::mapToDTO);
    } else if (genreName != null) {
        return bookRepository.findByGenresNameContainingIgnoreCase(genreName, pageable).map(this::mapToDTO);
    } else if (price != null) {
        return bookRepository.findByPrice(price, pageable).map(this::mapToDTO);
    } else {
        return bookRepository.findAll(pageable).map(this::mapToDTO);
    }
}


}
