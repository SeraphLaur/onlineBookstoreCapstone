package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.BookDto;
import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


/**
 * The type Book controller.
 */
@RestController
@RequestMapping("/api/books")
class BookController {

    private final BookService bookService;

    /**
     * Instantiates a new Book controller.
     *
     * @param bookService the book service
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Gets books.
     *
     * @param q        the q
     * @param category the category
     * @return the books
     */
    @GetMapping
    public Page<BookDto> getBooks(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "category", required = false) String category,
            @PageableDefault(size = 12, sort = "title") Pageable pageable
    ) {
        return bookService.search(q, category, pageable)
                .map(BookDto::fromEntity);
    }
}