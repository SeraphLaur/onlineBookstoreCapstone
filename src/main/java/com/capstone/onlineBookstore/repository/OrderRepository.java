package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByUser_Id(Long userId);
}
