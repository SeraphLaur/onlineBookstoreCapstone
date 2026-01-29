package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthor(String author);

    Optional<Book> findByCategory_Name(String categoryName);
    Optional<Book> findByIsbnIgnoreCase(String isbn);
    Optional<Book> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findByAuthorContainingIgnoreCase(String author);

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
    List<Book> search(@Param("q") String q, @Param("category") String category);
}


