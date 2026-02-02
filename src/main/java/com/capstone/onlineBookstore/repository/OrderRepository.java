package com.capstone.onlineBookstore.repository;

import com.capstone.onlineBookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Order> findByUser_Id(Long userId);
}
