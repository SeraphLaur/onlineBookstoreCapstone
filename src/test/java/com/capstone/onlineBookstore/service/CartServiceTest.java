package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.CartItem;
import com.capstone.onlineBookstore.model.User;
import com.capstone.onlineBookstore.repository.BookRepository;
import com.capstone.onlineBookstore.repository.CartRepository;
import com.capstone.onlineBookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void getOrCreateCartByUserId_ExistingCart_ReturnsCart() {
        //setup
        Cart cart = new Cart();
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));

        // execute
        Cart result = cartService.getOrCreateCartByUserId(1L);

        // verify
        assertNotNull(result);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void getOrCreateCartByUserId_NoCart_CreatesNewCart() {
        // setup
        User user = new User();
        Cart cart = new Cart();
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // execute
        Cart result = cartService.getOrCreateCartByUserId(1L);

        // verify
        assertNotNull(result);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addItemToCart_Success() {
        // setup
        Cart cart = new Cart();
        Book book = new Book();
        book.setId(1L);

        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Execute
        Cart result = cartService.addItemToCart(1L, 1L, 2);

        // Verify
        assertNotNull(result);
        verify(cartRepository).save(cart);
    }

    @Test
    void addItemToCart_InvalidQuantity_ThrowsException() {
        // Execute & Verify
        assertThrows(IllegalArgumentException.class, () -> {
            cartService.addItemToCart(1L, 1L, 0);
        });
    }

    @Test
    void setItemQuantity_Success() {

        Cart cart = new Cart();
        Book book = new Book();
        book.setId(1L);

        CartItem cartItem = new CartItem(cart,book, 2);
        cart.setCartItems(new ArrayList<>(List.of(cartItem)));

        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.setItemQuantity(1L, 1L, 5);
        assertNotNull(result);
        verify(cartRepository).save(cart);
    }

    @Test
    void removeItemFromCart_Success() {
        // setup
        Cart cart = new Cart();
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // execute
        Cart result = cartService.removeItemFromCart(1L, 1L);

        // verify
        assertNotNull(result);
        verify(cartRepository).save(cart);
    }

    @Test
    void clearCart_Success() {
        // setup
        Cart cart = new Cart();
        when(cartRepository.findByUser_Id(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // execute
        Cart result = cartService.clearCart(1L);

        // verify
        assertNotNull(result);
        verify(cartRepository).save(cart);
    }

}