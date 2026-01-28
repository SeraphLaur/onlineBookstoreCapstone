package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.OrderItem;
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
}
