package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_Id(Long id);
    Optional<CartItem> findByCart_IdAndBook_Id(Long cartId, Long bookId);
    void deleteByCart_IdAndBook_Id(Long cartId, Long bookId);
    boolean existsByCart_IdAndBook_Id(Long cartId, Long bookId);
}
