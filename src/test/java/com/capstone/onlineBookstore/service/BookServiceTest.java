package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    //return all books
    @Test
    void getAll_ReturnsAllBooks() {
        //setup
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        // execute and verify
        List<Book> result = bookService.getAll();
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    //test search query with category
    @Test
    void search_WithQueryAndCategory() {
        // setup
        when(bookRepository.search("java", "Programming")).thenReturn(Arrays.asList(new Book()));

        // execute
        List<Book> result = bookService.search("java", "Programming");

        // verify
        assertNotNull(result);
        verify(bookRepository).search("java", "Programming");
    }

    //search without category
    @Test
    void search_WithNullQuery_PassesNull() {
        // setup
        when(bookRepository.search(null, "Fiction")).thenReturn(Arrays.asList());

        // execute
        bookService.search(null, "Fiction");

        // Verify
        verify(bookRepository).search(null, "Fiction");
    }

}