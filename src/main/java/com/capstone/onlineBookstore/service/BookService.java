package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Book service.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Instantiates a new Book service.
     *
     * @param bookRepository the book repository
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    /**
     * Search list.
     *
     * @param q        the q
     * @param category the category
     * @return the list
     */
    public List<Book> search(String q, String category) {
        String qq = (q == null || q.isBlank()) ? null : q.trim();
        String cc = (category == null || category.isBlank() || "ALL".equalsIgnoreCase(category))
                ? null : category.trim();
        return bookRepository.search(qq, cc);
    }
}