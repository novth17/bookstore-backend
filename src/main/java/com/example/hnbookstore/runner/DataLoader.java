package com.example.hnbookstore.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.Category;
import com.example.hnbookstore.domain.BookRepository;
import com.example.hnbookstore.domain.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if categories exist before adding new ones
        if (categoryRepository.count() == 0) {
            List<Category> categories = List.of(
                new Category("Fiction"),
                new Category("Non-Fiction"),
                new Category("Science"),
                new Category("Technology"),
                new Category("History"),
                new Category("Health"),
                new Category("Biography"),
                new Category("Mystery"),
                new Category("Fantasy")
            );

            categoryRepository.saveAll(categories);
            System.out.println("📚 Categories added to the database.");
        }

        // Fetch categories for assigning to books
        Optional<Category> fictionCategory = categoryRepository.findByName("Fiction");
        Optional<Category> scienceCategory = categoryRepository.findByName("Science");

        if (fictionCategory.isPresent() && scienceCategory.isPresent()) {
            Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "9780743273565", 10.99, fictionCategory.get());
            Book book2 = new Book("1984", "George Orwell", "1949", "9780451524935", 8.99, scienceCategory.get());

            bookRepository.save(book1);
            bookRepository.save(book2);

            System.out.println("📚 Example books have been added to the database.");
        } else {
            System.out.println("⚠️ Could not find categories for books.");
        }
    }
}
