package com.example.hnbookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.BookRepository;
import com.example.hnbookstore.domain.Category;
import com.example.hnbookstore.domain.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    // Login Page Mapping
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Returns login.html from templates folder
    }

    // Show authenticated username on booklist page
    @GetMapping("/booklist")
    public String getBookList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName()); // Pass username to Thymeleaf template
        
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "booklist";
    }

    @GetMapping("/addbook")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    @PostMapping("/addbook")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam String publicationYear,
                          @RequestParam String isbn,
                          @RequestParam double price,
                          @RequestParam Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Book book = new Book(title, author, publicationYear, isbn, price, categoryOptional.get());
            bookRepository.save(book);
        }
        return "redirect:/booklist";
    }

    //Only ADMIN can delete books!!!!
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("categories", categoryRepository.findAll());
            return "editbook";
        } else {
            return "redirect:/booklist";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id,
                             @RequestParam String title,
                             @RequestParam String author,
                             @RequestParam String publicationYear,
                             @RequestParam String isbn,
                             @RequestParam double price,
                             @RequestParam Long categoryId) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        
        if (bookOptional.isPresent() && categoryOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublicationYear(publicationYear);
            book.setIsbn(isbn);
            book.setPrice(price);
            book.setCategory(categoryOptional.get());
            bookRepository.save(book);
        }
        return "redirect:/booklist";
    }
}
