package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.AuthorService;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookViewController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping
    public String listBooks(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String authorName,
        @RequestParam(required = false) String genreName,
        @RequestParam(required = false) BigDecimal price,
        Model model
    ) {
        model.addAttribute("books", bookService.searchBooks(title, authorName, genreName, price, Pageable.unpaged()).getContent());
        model.addAttribute("title", title);
        model.addAttribute("authorName", authorName);
        model.addAttribute("genreName", genreName);
        model.addAttribute("price", price);

        return "books/list";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "books/form";
    }

    @PostMapping
    public String saveBook(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "books/form";
        }
        bookService.save(bookDTO);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {
    BookDTO book = bookService.findById(id);
    model.addAttribute("book", book);
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "books/edit";
}

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "books/edit";
        }
        bookService.update(id, bookDTO);  
        return "redirect:/books";
    }
}
