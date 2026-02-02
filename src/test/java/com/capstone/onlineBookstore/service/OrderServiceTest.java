package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.*;
import com.capstone.onlineBookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderFromCart_Success() {
        // setup
        User user = new User();
        Cart cart = new Cart();
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setPrice(BigDecimal.valueOf(20.00));
        book.setStock(10);

        CartItem cartItem = new CartItem(cart,book, 2);
        cart.setCartItems(new ArrayList<>(List.of(cartItem)));

        Order order = new Order();
        order.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // execute
        Order result = orderService.createOrderFromCart(1L);

        // verify
        assertNotNull(result);
        verify(orderRepository, atLeastOnce()).save(any(Order.class));
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void createOrderFromCart_EmptyCart_ThrowsException() {
        // setup
        User user = new User();
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));

        // execute & verify
        assertThrows(IllegalStateException.class, () -> {
            orderService.createOrderFromCart(1L);
        });
    }

    @Test
    void createOrderFromCart_InsufficientStock_ThrowsException() {
        // setup
        User user = new User();
        Cart cart = new Cart();
        Book book = new Book();
        book.setId(1L);
        book.setStock(1);

        CartItem cartItem = new CartItem(cart, book, 5);
        cartItem.setBook(book);
        cartItem.setQuantity(5);
        cart.setCartItems(List.of(cartItem));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));

        // execute & Verify
        assertThrows(IllegalStateException.class, () -> {
            orderService.createOrderFromCart(1L);
        });
    }

    @Test
    void getOrder_Success() {
        // setup
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // execute
        Order result = orderService.getOrder(1L);

        // verify
        assertNotNull(result);
    }

    @Test
    void getOrder_NotFound_ThrowsException() {
        // setup
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // execute & verify
        assertThrows(EntityNotFoundException.class, () -> {
            orderService.getOrder(1L);
        });
    }

    @Test
    void getOrdersForUser_Success() {
        // setup
        List<Order> orders = List.of(new Order(), new Order());
        when(orderRepository.findByUser_Id(1L)).thenReturn(orders);

        // execute
        List<Order> result = orderService.getOrdersForUser(1L);

        // verify
        assertEquals(2, result.size());
    }

    @Test
    void updateStatus_Success() {
        // setup
        Order order = new Order();
        order.setStatus("PENDING");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // execute
        Order result = orderService.updateStatus(1L, "PROCESSING");

        // verify
        assertNotNull(result);
        verify(orderRepository).save(order);
    }

    @Test
    void updateStatus_InvalidTransition_ThrowsException() {
        // setup
        Order order = new Order();
        order.setStatus("COMPLETED");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // execute & Verify
        assertThrows(IllegalStateException.class, () -> {
            orderService.updateStatus(1L, "PENDING");
        });
    }

    @Test
    void cancelOrder_Success() {
        // setup
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");

        Book book = new Book();
        book.setStock(5);

        OrderItem orderItem = new OrderItem(order, book, 2, BigDecimal.valueOf(20.00));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder_Id(1L)).thenReturn(List.of(orderItem));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // execute
        Order result = orderService.cancelOrder(1L);

        // verify
        assertNotNull(result);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void getOrderItems_Success() {
        // setup
        Order order = new Order();
        Book book = new Book();

        // use the public constructor
        OrderItem item1 = new OrderItem(order, book, 1, BigDecimal.valueOf(10.00));
        OrderItem item2 = new OrderItem(order, book, 2, BigDecimal.valueOf(15.00));

        List<OrderItem> items = List.of(item1, item2);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder_Id(1L)).thenReturn(items);

        // execute
        List<OrderItem> result = orderService.getOrderItems(1L);

        // verify
        assertEquals(2, result.size());
    }
}