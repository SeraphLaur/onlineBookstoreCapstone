package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.CartMapper;
import com.capstone.onlineBookstore.dto.CartSummaryDto;
import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.service.CartService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * The type Cart controller.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    /**
     * Instantiates a new Cart controller.
     *
     * @param cartService the cart service
     */
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * The type Add to cart request.
     */
    public static class AddToCartRequest {
        /**
         * The Book id.
         */
        @NotNull public Long bookId;
        /**
         * The Qty.
         */
        @Min(1)  public int qty = 1;
    }

    /**
     * The type Set qty request.
     */
    public static class SetQtyRequest {
        /**
         * The Book id.
         */
        @NotNull public Long bookId;
        /**
         * The Qty.
         */
        @Min(0)  public int qty;
    }

    /**
     * Gets cart.
     *
     * @param userId the user id
     * @return the cart
     */
    @GetMapping
    public CartSummaryDto getCart(@AuthenticationPrincipal(expression = "id") Long userId) {
        Cart cart = cartService.getOrCreateCartByUserId(userId);
        return CartMapper.toDto(cart);
    }

    /**
     * Add item cart summary dto.
     *
     * @param userId the user id
     * @param req    the req
     * @return the cart summary dto
     */
    @PostMapping("/items")
    public CartSummaryDto addItem(@AuthenticationPrincipal(expression = "id") Long userId,
                                  @RequestBody AddToCartRequest req) {
        Cart cart = cartService.addItemToCart(userId, req.bookId, req.qty);
        return CartMapper.toDto(cart);
    }


    /**
     * Sets quantity.
     *
     * @param userId the user id
     * @param req    the req
     * @return the quantity
     */
    @PatchMapping("/items/quantity")
    public CartSummaryDto setQuantity(@AuthenticationPrincipal(expression = "id") Long userId,
                                      @RequestBody SetQtyRequest req) {
        Cart cart = cartService.setItemQuantity(userId, req.bookId, req.qty);
        return CartMapper.toDto(cart);
    }


    /**
     * Remove item cart summary dto.
     *
     * @param userId the user id
     * @param bookId the book id
     * @return the cart summary dto
     */
    @DeleteMapping("/items/{bookId}")
    public CartSummaryDto removeItem(@AuthenticationPrincipal(expression = "id") Long userId,
                                     @PathVariable Long bookId) {
        Cart cart = cartService.removeItemFromCart(userId, bookId);
        return CartMapper.toDto(cart);
    }

    /**
     * Clear cart summary dto.
     *
     * @param userId the user id
     * @return the cart summary dto
     */
    @DeleteMapping
    public CartSummaryDto clear(@AuthenticationPrincipal(expression = "id") Long userId) {
        Cart cart = cartService.clearCart(userId);
        return CartMapper.toDto(cart);
    }
}