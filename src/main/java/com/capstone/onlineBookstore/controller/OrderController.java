package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.OrderDto;
import com.capstone.onlineBookstore.dto.OrderItemDto;
import com.capstone.onlineBookstore.model.Order;
import com.capstone.onlineBookstore.model.OrderItem;
import com.capstone.onlineBookstore.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * The type Update order status request.
     */
    public static class UpdateOrderStatusRequest {
        /**
         * The Status.
         */
        public String status; }

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create from cart response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
// create an order from  cart
    @PostMapping
    public ResponseEntity<OrderDto> createFromCart(@AuthenticationPrincipal(expression = "id") Long userId) {
        Order order = orderService.createOrderFromCart(userId);
        List<OrderItem> items = orderService.getOrderItems(order.getId());
        return ResponseEntity.ok(toDto(order, items));
    }

    /**
     * My orders list.
     *
     * @param userId the user id
     * @return the list
     */
// list ALL the current orders for users
    @GetMapping("/me")
    public List<OrderDto> myOrders(@AuthenticationPrincipal(expression = "id") Long userId) {
        return orderService.getOrdersForUser(userId)
                .stream()
                .map(o -> toDto(o, orderService.getOrderItems(o.getId())))
                .collect(toList());
    }

    /**
     * Get order dto.
     *
     * @param id     the id
     * @param userId the user id
     * @return the order dto
     */
// get a specific order by id
    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id,
                        @AuthenticationPrincipal(expression = "id") Long userId) {
        Order o = orderService.getOrder(id);
        return toDto(o, orderService.getOrderItems(id));
    }

    /**
     * Items list.
     *
     * @param id     the id
     * @param userId the user id
     * @return the list
     */
// get the associated items for the specific order
    @GetMapping("/{id}/items")
    public List<OrderItemDto> items(@PathVariable Long id,
                                    @AuthenticationPrincipal(expression = "id") Long userId) {
        orderService.getOrder(id);
        return orderService.getOrderItems(id).stream().map(this::toItemDto).collect(toList());
    }

    //for updating status but this is for admin
//    @PatchMapping("/{id}/status")
//    public OrderDto updateStatus(@PathVariable Long id,
//                                 @RequestBody UpdateOrderStatusRequest req) {
//        Order updated = orderService.updateStatus(id, req.status);
//        return toDto(updated, orderService.getOrderItems(id));
//    }

    /**
     * Cancel order dto.
     *
     * @param id     the id
     * @param userId the user id
     * @return the order dto
     */
// for cancelling orders
    @PostMapping("/{id}/cancel")
    public OrderDto cancel(@PathVariable Long id,
                           @AuthenticationPrincipal(expression = "id") Long userId) {

        Order cancelled = orderService.cancelOrder(id);
        return toDto(cancelled, orderService.getOrderItems(id));
    }

    // mapping of dto
    private OrderDto toDto(Order o, List<OrderItem> items) {
        OrderDto d = new OrderDto();
        d.id = o.getId();
        d.userId = o.getUser().getId();
        d.status = o.getStatus();
        d.total = o.getTotal();
        d.items = items.stream().map(this::toItemDto).collect(toList());
        return d;
    }

    private OrderItemDto toItemDto(OrderItem oi) {
        OrderItemDto d = new OrderItemDto();
        d.id = oi.getId();
        d.bookId = oi.getBook().getId();
        d.bookTitle = oi.getBook().getTitle();
        d.quantity = oi.getQuantity();
        d.unitPrice = oi.getUnitPrice();
        d.lineTotal = oi.getLineTotal();
        return d;
    }

    /**
     * Handle bad requests response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({EntityNotFoundException.class, IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleBadRequests(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}