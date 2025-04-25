package com.example.bookstore.service;

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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public Book saveBook(Book book) {
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Genre> genres = genreRepository.findAllById(
                book.getGenres().stream().map(Genre::getId).toList()
        );

        book.setAuthor(author);
        book.setGenres(genres);

        return bookRepository.save(book);
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> searchBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitle(title, pageable);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Author author = authorRepository.findById(updatedBook.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Genre> genres = genreRepository.findAllById(
                updatedBook.getGenres().stream().map(Genre::getId).toList()
        );

        book.setTitle(updatedBook.getTitle());
        book.setDescription(updatedBook.getDescription());
        book.setPrice(updatedBook.getPrice());
        book.setAuthor(author);
        book.setGenres(genres);

        return bookRepository.save(book);
    }

    public Page<Book> searchBooksByPrice(Double price, Pageable pageable) {
        return bookRepository.findByPrice(price, pageable);
    }
}