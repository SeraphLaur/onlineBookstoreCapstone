package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.dto.BookCreateUpdateDto;
import com.capstone.onlineBookstore.dto.BookDto;
import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.repository.BookRepository;
import com.capstone.onlineBookstore.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminBookService{

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public AdminBookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto addNewBook(BookCreateUpdateDto in){
        Book b = new Book();
        b.setIsbn(in.isbn());
        b.setTitle(in.title());
        b.setAuthor(in.author());
        b.setDescription(in.description());
        b.setPrice(in.price());
        b.setStock(in.stock());
        b.setImageUrl(in.imageUrl());
        b.setCategory(categoryRepository.findById(in.categoryId())
        .orElseThrow(() -> new RuntimeException("Category not found")));

        return BookDto.fromEntity(bookRepository.save(b));




    }
}
