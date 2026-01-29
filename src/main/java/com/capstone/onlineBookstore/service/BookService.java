package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> search(String q, String category) {
        String qq = (q == null || q.isBlank()) ? null : q.trim();
        String cc = (category == null || category.isBlank() || "ALL".equalsIgnoreCase(category))
                ? null : category.trim();
        return bookRepository.search(qq, cc);
    }
}