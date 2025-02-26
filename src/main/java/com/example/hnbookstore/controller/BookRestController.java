package com.example.hnbookstore.controller;

import org.springframework.web.bind.annotation.*;
import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.BookRepository;

import java.util.List;

@RestController 
@RequestMapping("/api/books")
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }
}
