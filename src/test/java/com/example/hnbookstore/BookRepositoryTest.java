package com.example.hnbookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import com.example.hnbookstore.domain.Book;
import com.example.hnbookstore.domain.BookRepository;
import com.example.hnbookstore.domain.Category;
import com.example.hnbookstore.domain.CategoryRepository;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest { 

    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository crepository;
    
    @Test
    public void findByTitleShouldReturnBook() { 
        
        Category category = new Category("Vampire");
        crepository.save(category);

        Book book = new Book("The Hobbit", "J.R.R. Tolkien", "1937", "978-0345339683", 19.99, category);
        repository.save(book);

   
        List<Book> books = repository.findByTitle("The Hobbit");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("J.R.R. Tolkien");
    }
    
    @Test
    public void createNewBook() {
        
        Category category = new Category("Vampire");
        crepository.save(category);

        // Save book
        Book book = new Book("Harry Potter", "J.K. Rowling", "1997", "978-0747532699", 29.99, category);
        repository.save(book);

        assertThat(book.getId()).isNotNull();
    }    

    @Test
    public void deleteBook() {
 
        List<Book> books = repository.findByTitle("The Hobbit");
        
        if (!books.isEmpty()) {  
            Book book = books.get(0);
            repository.delete(book);
        }

        List<Book> newBooks = repository.findByTitle("The Hobbit");
        assertThat(newBooks).isEmpty(); 
    }
}
