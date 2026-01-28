package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthor(String author);

    Optional<Book> findByCategory_Name(String categoryName);
    Optional<Book> findByIsbnIgnoreCase(String isbn);
    Optional<Book> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findByAuthorContainingIgnoreCase(String author);
}
