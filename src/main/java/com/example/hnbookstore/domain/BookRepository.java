package com.example.hnbookstore.domain;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByTitle(String title);
    Book findByAuthor(String author);
    Book findByPublicationYear(String publicationYear);
    Book findByIsbn(String isbn);
    Book findByPrice(String price);
}
