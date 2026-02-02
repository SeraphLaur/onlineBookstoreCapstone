package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Cart item repository.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * Find by cart id list.
     *
     * @param id the id
     * @return the list
     */
    List<CartItem> findByCart_Id(Long id);

    /**
     * Find by cart id and book id optional.
     *
     * @param cartId the cart id
     * @param bookId the book id
     * @return the optional
     */
    Optional<CartItem> findByCart_IdAndBook_Id(Long cartId, Long bookId);

}
