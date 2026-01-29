package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.model.Book;
import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.User;
import com.capstone.onlineBookstore.repository.BookRepository;
import com.capstone.onlineBookstore.repository.CartItemRepository;
import com.capstone.onlineBookstore.repository.CartRepository;
import com.capstone.onlineBookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public CartService(CartRepository cartRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    //get or create a cart record if there are no records found
    @Transactional
    public Cart getOrCreateCartByUserId(Long userId){
        return cartRepository.findByUser_Id(userId)
                .orElseGet(()-> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new EntityNotFoundException("User not found " + userId));
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });

    }

    //add item to the cart(create a cart if the user hasnt one yet
    @Transactional
    public Cart addItemToCart(Long userId, Long bookId, int qty){
        if(qty <= 0){
            throw new IllegalArgumentException("Value should not be less than or equals to 0!");
        }

        Cart cart = getOrCreateCartByUserId(userId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found " + bookId));

        cart.addOrIncreaseItem(book, qty);
        return cartRepository.save(cart);
    }

    //set custom quantity for an item
    @Transactional
    public Cart setItemQuantity(Long userId,Long bookId, int qty){
        Cart cart= cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + userId));

        if (qty <= 0){
            boolean removed = cart.removeItem(bookId);
            if (!removed){
                throw new EntityNotFoundException("Item not found " + bookId);
            }
        } else {
            cart.setItemQuantity(bookId, qty);
        }
        return cartRepository.save(cart);
    }

    //delete specific item in the cart
    @Transactional
    public Cart removeItemFromCart(Long userId, Long bookId){
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + userId));
        boolean removed = cart.removeItem(bookId);
        if (!removed){
            throw new EntityNotFoundException("Item not found " + bookId);
        }
        return cartRepository.save(cart);

    }

    //clear all items in the cart
    @Transactional
    public Cart clearCart(Long userId){
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + userId));
        cart.clear();
        return cartRepository.save(cart);
    }

}
