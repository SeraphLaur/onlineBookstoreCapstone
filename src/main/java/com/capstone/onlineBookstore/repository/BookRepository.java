package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The interface Book repository.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Exists by author boolean.
     *
     * @param author the author
     * @return the boolean
     */
    boolean existsByAuthor(String author);

    /**
     * Find by category name optional.
     *
     * @param categoryName the category name
     * @return the optional
     */
    Optional<Book> findByCategory_Name(String categoryName);

    /**
     * Find by isbn ignore case optional.
     *
     * @param isbn the isbn
     * @return the optional
     */
    Optional<Book> findByIsbnIgnoreCase(String isbn);

    /**
     * Find by title containing ignore case optional.
     *
     * @param title the title
     * @return the optional
     */
    Optional<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Find by author containing ignore case optional.
     *
     * @param author the author
     * @return the optional
     */
    Optional<Book> findByAuthorContainingIgnoreCase(String author);

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
    List<Book> search(@Param("q") String q, @Param("category") String category);
}


