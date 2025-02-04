package com.example.hnbookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("this.bookRepository: " + this.bookRepository);
    }

    @GetMapping("/booklist")
    public String getBookList(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("books", books);
        return "booklist";
    }
}
