package com.example.hnbookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/addbook")
    public String addBookForm() {
        return "addbook";
    }

    @PostMapping("/addbook")
    public String addBook(String title, String author, String publicationYear, String isbn, double price) {
        Book book = new Book(title, author, publicationYear, isbn, price);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }
}
