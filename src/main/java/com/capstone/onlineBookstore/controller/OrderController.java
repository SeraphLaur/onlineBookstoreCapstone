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

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public static class UpdateOrderStatusRequest { public String status; }

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // create an order from  cart
    @PostMapping
    public ResponseEntity<OrderDto> createFromCart(@AuthenticationPrincipal(expression = "id") Long userId) {
        Order order = orderService.createOrderFromCart(userId);
        List<OrderItem> items = orderService.getOrderItems(order.getId());
        return ResponseEntity.ok(toDto(order, items));
    }

    // list ALL the current orders for users
    @GetMapping("/me")
    public List<OrderDto> myOrders(@AuthenticationPrincipal(expression = "id") Long userId) {
        return orderService.getOrdersForUser(userId)
                .stream()
                .map(o -> toDto(o, orderService.getOrderItems(o.getId())))
                .collect(toList());
    }

    // get a specific order by id
    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id,
                        @AuthenticationPrincipal(expression = "id") Long userId) {
        Order o = orderService.getOrder(id);
        return toDto(o, orderService.getOrderItems(id));
    }

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

    @ExceptionHandler({EntityNotFoundException.class, IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleBadRequests(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}