package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.*;
import com.capstone.onlineBookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

/**
 * The type Order service.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository; // assumes you have this
    private final UserRepository userRepository; // assumes you have this

    /**
     * The enum Order status.
     */
    public enum OrderStatus {
        /**
         * Pending order status.
         */
        PENDING,
        /**
         * Processing order status.
         */
        PROCESSING,
        /**
         * Shipped order status.
         */
        SHIPPED,
        /**
         * Completed order status.
         */
        COMPLETED,
        /**
         * Cancelled order status.
         */
        CANCELLED
    }

    private static final EnumSet<OrderStatus> CANCELLABLE_STATUSES =
            EnumSet.of(OrderStatus.PENDING, OrderStatus.PROCESSING);

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository     the order repository
     * @param orderItemRepository the order item repository
     * @param cartRepository      the cart repository
     * @param bookRepository      the book repository
     * @param userRepository      the user repository
     */
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        BookRepository bookRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create order from cart order.
     *
     * @param userId the user id
     * @return the order
     */
    @Transactional
    public Order createOrderFromCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user " + userId));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        // validate stock first
        cart.getCartItems().forEach(ci -> {
            Book b = ci.getBook();
            if (b.getStock() == null || b.getStock() < ci.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for book id " + b.getId() + " (" + b.getTitle() + ")");
            }
        });

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING.name());
        order.setTotal(BigDecimal.ZERO);
        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        // convert cart items to order items, decrement stock
        for (CartItem ci : cart.getCartItems()) {
            Book b = ci.getBook();
            int qty = ci.getQuantity();

            // Decrement stock
            b.setStock(b.getStock() - qty);
            bookRepository.save(b);

            // create order item with snapshot price
            BigDecimal unitPrice = b.getPrice() == null ? BigDecimal.ZERO : b.getPrice();
            var oi = new OrderItem(order, b, qty, unitPrice);
            orderItemRepository.save(oi);

            total = total.add(oi.getLineTotal());
        }

        order.setTotal(total);
        order = orderRepository.save(order);

        // clear the cart (implementation depends on your Cart entity)
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return order;
    }

    /**
     * Gets order.
     *
     * @param orderId the order id
     * @return the order
     */
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
    }

    /**
     * Gets orders for user.
     *
     * @param userId the user id
     * @return the orders for user
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }

    /**
     * Update status order.
     *
     * @param orderId      the order id
     * @param newStatusRaw the new status raw
     * @return the order
     */
    @Transactional
    public Order updateStatus(Long orderId, String newStatusRaw) {
        Order order = getOrder(orderId);
        OrderStatus newStatus = parseStatus(newStatusRaw);

        // basic state machine constraints
        OrderStatus current = OrderStatus.valueOf(order.getStatus());
        if (current == OrderStatus.CANCELLED || current == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot change status from " + current);
        }
        if (current == OrderStatus.PENDING && newStatus == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot go directly from PENDING to COMPLETED");
        }
        if (current == OrderStatus.SHIPPED && newStatus == OrderStatus.PROCESSING) {
            throw new IllegalStateException("Invalid backward transition");
        }

        order.setStatus(newStatus.name());
        return orderRepository.save(order);
    }

    /**
     * Cancel order.
     *
     * @param orderId the order id
     * @return the order
     */
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getOrder(orderId);
        OrderStatus current = OrderStatus.valueOf(order.getStatus());
        if (!CANCELLABLE_STATUSES.contains(current)) {
            throw new IllegalStateException("Order cannot be cancelled from status " + current);
        }
        // Restock on cancel
        List<OrderItem> items = orderItemRepository.findByOrder_Id(order.getId());
        for (OrderItem oi : items) {
            Book b = oi.getBook();
            b.setStock(b.getStock() + oi.getQuantity());
            bookRepository.save(b);
        }
        order.setStatus(OrderStatus.CANCELLED.name());
        return orderRepository.save(order);
    }

    /**
     * Gets order items.
     *
     * @param orderId the order id
     * @return the order items
     */
    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItems(Long orderId) {
        // ensure order exists
        getOrder(orderId);
        return orderItemRepository.findByOrder_Id(orderId);
    }

    private OrderStatus parseStatus(String raw) {
        try {
            return OrderStatus.valueOf(raw == null ? "" : raw.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid status. Allowed: " + EnumSet.allOf(OrderStatus.class));
        }
    }
}