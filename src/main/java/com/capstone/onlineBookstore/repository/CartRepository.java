package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Id(Long userId);
}
