package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.BookCreateUpdateDto;
import com.capstone.onlineBookstore.dto.BookDto;
import com.capstone.onlineBookstore.service.AdminBookService;
import com.capstone.onlineBookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
class AdminBookController {

    private final AdminBookService adminBookService;
    private final BookService bookService;

    public AdminBookController(AdminBookService adminBookService,  BookService bookService) {
        this.adminBookService = adminBookService;
        this.bookService = bookService;
    }

    @GetMapping
    public Page<BookDto> getBooks(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "category", required = false) String category,
            @PageableDefault(size = 12, sort = "Title") Pageable pageable
    ) {
        return bookService.search(q, category, pageable)
                .map(BookDto::fromEntity);
    }


    @PostMapping
    public BookDto createBook(@RequestBody BookCreateUpdateDto bookDto) {
        return adminBookService.addNewBook(bookDto);
    }


}
