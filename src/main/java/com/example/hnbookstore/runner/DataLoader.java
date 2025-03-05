package com.example.hnbookstore.runner;

import com.example.hnbookstore.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookUserRepository bookUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(BookRepository bookRepository, 
                      CategoryRepository categoryRepository, 
                      BookUserRepository bookUserRepository, 
                      PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookUserRepository = bookUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadBooks();
        loadUsers();
    }

    // Load Categories into the Database
    private void loadCategories() {
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
    }

    // Load Books into the Database
    private void loadBooks() {
        Optional<Category> fictionCategory = categoryRepository.findByName("Fiction");
        Optional<Category> scienceCategory = categoryRepository.findByName("Science");

        if (fictionCategory.isPresent() && scienceCategory.isPresent()) {
            if (bookRepository.count() == 0) {
                Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "9780743273565", 10.99, fictionCategory.get());
                Book book2 = new Book("1984", "George Orwell", "1949", "9780451524935", 8.99, scienceCategory.get());

                bookRepository.save(book1);
                bookRepository.save(book2);

                System.out.println("📚 Example books have been added to the database.");
            }
        } else {
            System.out.println("⚠️ Could not find categories for books.");
        }
    }

    // Load Users into the Database with BCrypt Hashed Passwords
    private void loadUsers() {
        if (bookUserRepository.count() == 0) {
            BookUser admin = new BookUser("admin", passwordEncoder.encode("adminpass"), "admin@example.com", "ROLE_ADMIN");
            BookUser user = new BookUser("user", passwordEncoder.encode("password"), "user@example.com", "ROLE_USER");

            bookUserRepository.save(admin);
            bookUserRepository.save(user);

            System.out.println("Users added to the database.");
        }
    }
}
