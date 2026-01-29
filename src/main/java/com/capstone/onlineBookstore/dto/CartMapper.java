package com.capstone.onlineBookstore.dto;

import com.capstone.onlineBookstore.model.Cart;
import com.capstone.onlineBookstore.model.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public final class CartMapper {
    private CartMapper() {}

    public static CartSummaryDto toDto(Cart cart) {
        // Map lines
        List<CartLineDto> lines = cart.getCartItems().stream()
                .map(CartMapper::toLineDto)
                .collect(Collectors.toList());

        // Sum subtotal from the mapped lines
        BigDecimal subtotal = lines.stream()
                .map(CartLineDto::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartSummaryDto(
                cart.getId(),
                cart.getUser().getId(),
                cart.getTotalQuantity(),
                cart.getDistinctItemsCount(),
                subtotal,
                lines
        );
    }

    private static CartLineDto toLineDto(CartItem ci) {
        var book = ci.getBook();
        var price = book.getPrice(); // BigDecimal field on Book
        var qty = ci.getQuantity();
        var lineTotal = price.multiply(BigDecimal.valueOf(qty));

        return new CartLineDto(
                book.getId(),
                book.getTitle(),
                price,
                qty,
                lineTotal
        );
    }
}