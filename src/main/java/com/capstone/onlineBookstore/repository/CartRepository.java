package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Cart repository.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<Cart> findByUser_Id(Long userId);
}
