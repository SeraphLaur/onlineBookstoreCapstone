package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Order item repository.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    /**
     * Find by order id list.
     *
     * @param orderId the order id
     * @return the list
     */
    List<OrderItem> findByOrder_Id(Long orderId);
}
