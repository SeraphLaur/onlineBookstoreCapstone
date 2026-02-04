package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.data.domain.Page;               // <-- correct
import org.springframework.data.domain.Pageable;          // <-- correct

import java.util.List;
import java.util.Optional;

/**
 * The interface Book repository.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Search list.
     *
     * @param q        the q
     * @param category the category
     * @return the list
     */
    @Query("""
           select b
           from Book b
           where (:q is null or
                 lower(b.title)  like lower(concat('%', :q, '%')) or
                 lower(b.author) like lower(concat('%', :q, '%')) or
                 lower(b.isbn)   like lower(concat('%', :q, '%')))
             and (:category is null or lower(b.category.name) = lower(:category))
           order by b.title asc
           """)
    Page<Book> search(@Param("q") String q, @Param("category") String category, Pageable pageable);
}


