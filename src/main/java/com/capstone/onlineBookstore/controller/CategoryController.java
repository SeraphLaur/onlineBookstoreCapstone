package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.CategoryDto;
import com.capstone.onlineBookstore.model.Category;
import com.capstone.onlineBookstore.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

/**
 * The type Category controller.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    /**
     * Instantiates a new Category controller.
     *
     * @param categoryRepository the category repository
     */
    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    /**
     * List list.
     *
     * @return the list
     */
    @GetMapping
    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER))
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .toList();
    }
}
