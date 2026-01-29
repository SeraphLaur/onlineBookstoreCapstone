package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.CartMapper;
import com.capstone.onlineBookstore.dto.CartSummaryDto;
import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.service.CartService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    public static class AddToCartRequest {
        @NotNull public Long bookId;
        @Min(1)  public int qty = 1;
    }
    public static class SetQtyRequest {
        @NotNull public Long bookId;
        @Min(0)  public int qty;
    }

    @GetMapping
    public CartSummaryDto getCart(@AuthenticationPrincipal(expression = "id") Long userId) {
        Cart cart = cartService.getOrCreateCartByUserId(userId);
        return CartMapper.toDto(cart);
    }

    @PostMapping("/items")
    public CartSummaryDto addItem(@AuthenticationPrincipal(expression = "id") Long userId,
                                  @RequestBody AddToCartRequest req) {
        Cart cart = cartService.addItemToCart(userId, req.bookId, req.qty);
        return CartMapper.toDto(cart);
    }


    @PatchMapping("/items/quantity")
    public CartSummaryDto setQuantity(@AuthenticationPrincipal(expression = "id") Long userId,
                                      @RequestBody SetQtyRequest req) {
        Cart cart = cartService.setItemQuantity(userId, req.bookId, req.qty);
        return CartMapper.toDto(cart);
    }


    @DeleteMapping("/items/{bookId}")
    public CartSummaryDto removeItem(@AuthenticationPrincipal(expression = "id") Long userId,
                                     @PathVariable Long bookId) {
        Cart cart = cartService.removeItemFromCart(userId, bookId);
        return CartMapper.toDto(cart);
    }

    @DeleteMapping
    public CartSummaryDto clear(@AuthenticationPrincipal(expression = "id") Long userId) {
        Cart cart = cartService.clearCart(userId);
        return CartMapper.toDto(cart);
    }
}