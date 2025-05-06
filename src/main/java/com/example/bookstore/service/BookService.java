package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.GenreRepository;
import com.example.bookstore.repository.OrderItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private final OrderItemRepository orderItemRepository;

    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::mapToDTO);
    }

    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return mapToDTO(book);
    }

    public BookDTO save(BookDTO dto) {
        Book book;
    
        if (dto.getId() != null) {
            book = bookRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
        } else {
            book = new Book();
        }
    
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
    
        if (dto.getAuthorName() == null || dto.getAuthorName().isBlank()) {
            throw new IllegalArgumentException("Author name must not be null or blank");
        }
    
        Author author = authorRepository.findByNameIgnoreCase(dto.getAuthorName())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(dto.getAuthorName());
                    return authorRepository.save(newAuthor);
                });
        book.setAuthor(author);
    
        if (dto.getGenreNamesRaw() != null && !dto.getGenreNamesRaw().isBlank()) {
            List<Genre> genres = List.of(dto.getGenreNamesRaw().split(",")).stream()
                    .map(String::trim)
                    .filter(name -> !name.isBlank())
                    .map(name -> genreRepository.findByNameIgnoreCase(name)
                            .orElseGet(() -> {
                                Genre newGenre = new Genre();
                                newGenre.setName(name);
                                return genreRepository.save(newGenre);
                            }))
                    .collect(Collectors.toList());
            book.setGenres(genres);
        }
    
        Book saved = bookRepository.save(book);
        return mapToDTO(saved);
    }
    
    
    @Transactional
    public void delete(Long bookId) {
        orderItemRepository.deleteByBookId(bookId); 
        bookRepository.deleteById(bookId);        
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getName());
        }

        if (book.getGenres() != null) {
            dto.setGenreIds(book.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList()));
            dto.setGenreNames(book.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.toList()));
            dto.setGenreNamesRaw(book.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", ")));
        }

        return dto;
    }

    public Page<BookDTO> searchBooks(String title, String authorName, String genreName, BigDecimal price, Pageable pageable) {
        if (title != null && !title.isBlank()) {
            return bookRepository.findByTitleContainingIgnoreCase(title, pageable).map(this::mapToDTO);
        } else if (authorName != null && !authorName.isBlank()) {
            return bookRepository.findByAuthorNameContainingIgnoreCase(authorName, pageable).map(this::mapToDTO);
        } else if (genreName != null && !genreName.isBlank()) {
            return bookRepository.findByGenresNameContainingIgnoreCase(genreName, pageable).map(this::mapToDTO);
        } else if (price != null) {
            return bookRepository.findByPrice(price, pageable).map(this::mapToDTO);
        } else {
            return bookRepository.findAll(pageable).map(this::mapToDTO);
        }
    }
    

    public BookDTO update(Long bookId, BookDTO dto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
    
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
    
        if (dto.getAuthorName() == null || dto.getAuthorName().isBlank()) {
            throw new IllegalArgumentException("Author name must not be null or blank");
        }
    
        Author author = authorRepository.findByNameIgnoreCase(dto.getAuthorName())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(dto.getAuthorName());
                    return authorRepository.save(newAuthor);
                });
        book.setAuthor(author);
    
        if (dto.getGenreNamesRaw() != null && !dto.getGenreNamesRaw().isBlank()) {
            List<Genre> genres = List.of(dto.getGenreNamesRaw().split(",")).stream()
                    .map(String::trim)
                    .filter(name -> !name.isBlank())
                    .map(name -> genreRepository.findByNameIgnoreCase(name)
                            .orElseGet(() -> {
                                Genre newGenre = new Genre();
                                newGenre.setName(name);
                                return genreRepository.save(newGenre);
                            }))
                    .collect(Collectors.toList());
            book.setGenres(genres);
        }
    
        Book updatedBook = bookRepository.save(book);
    
        return mapToDTO(updatedBook);
    }
}
