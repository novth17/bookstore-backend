package com.example.hnbookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.Category;
import com.example.hnbookstore.domain.CategoryRepository;
import com.example.hnbookstore.domain.BookRepository;
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

    @GetMapping("/booklist")
    public String getBookList(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("books", books);
        return "booklist";
    }

    @GetMapping("/addbook")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll()); // Load categories for dropdown
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
            model.addAttribute("categories", categoryRepository.findAll()); // Load categories
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
            book.setCategory(categoryOptional.get()); // Assign new category
            bookRepository.save(book);
        }
        return "redirect:/booklist";
    }
}
