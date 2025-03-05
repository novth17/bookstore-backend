package com.example.hnbookstore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, Long> {
    Optional<BookUser> findByUsername(String username);
}
