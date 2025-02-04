package com.example.hnbookstore.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.BookRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Inserting some example books
        Book book1 = new Book();
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setPublicationYear("1925");
        book1.setIsbn("9780743273565");
        book1.setPrice(10.99);
        
        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setAuthor("George Orwell");
        book2.setPublicationYear("1949");
        book2.setIsbn("9780451524935");
        book2.setPrice(8.99);

        // Save the books to the database
        bookRepository.save(book1);
        bookRepository.save(book2);

        System.out.println("Example books have been added to the database.");
    }
}
