package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Cart summary dto.
 */
public record CartSummaryDto(
        Long cartId,
        Long userId,
        int totalItems,        // total quantity across all lines
        int distinctItems,     // number of distinct books
        BigDecimal subtotal,   // sum of line totals
        List<CartLineDto> lines
) {}