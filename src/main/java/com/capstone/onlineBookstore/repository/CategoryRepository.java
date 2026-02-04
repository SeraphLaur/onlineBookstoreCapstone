package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Category repository.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
