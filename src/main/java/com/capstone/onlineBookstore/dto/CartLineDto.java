package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;

/**
 * The type Cart line dto.
 */
public record CartLineDto(
        Long bookId,
        String title,
        BigDecimal price,
        int quantity,
        BigDecimal lineTotal
) {}