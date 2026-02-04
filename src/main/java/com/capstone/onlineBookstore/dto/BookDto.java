package com.capstone.onlineBookstore.dto;

import com.capstone.onlineBookstore.controller.CategoryController;
import com.capstone.onlineBookstore.model.Book;

import java.math.BigDecimal;

public record BookDto(
        Long id,
        String isbn,
        String title,
        String author,
        String description,
        CategoryDto category,
        BigDecimal price,
        Integer stock,
        String imageUrl
) {
    public static BookDto fromEntity(Book b) {
        return new BookDto(
                b.getId(),
                b.getIsbn(),
                b.getTitle(),
                b.getAuthor(),
                b.getDescription(),
                b.getCategory() == null ? null :
                        new CategoryDto(b.getCategory().getId(), b.getCategory().getName()),
                b.getPrice(),
                b.getStock(),
                b.getImageUrl()
        );
    }
}