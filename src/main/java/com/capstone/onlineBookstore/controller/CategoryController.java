package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.model.Category;
import com.capstone.onlineBookstore.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
class CategoryController {

    private final CategoryRepository categoryRepository;

    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    record CategoryDto(int id, String name) {}

    @GetMapping
    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER))
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .toList();
    }
}
