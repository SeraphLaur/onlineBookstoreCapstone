package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder_Id(Long orderId);
}
