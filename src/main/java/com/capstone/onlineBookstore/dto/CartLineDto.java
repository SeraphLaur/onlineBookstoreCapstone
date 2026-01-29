package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;

public record CartLineDto(
        Long bookId,
        String title,
        BigDecimal price,
        int quantity,
        BigDecimal lineTotal
) {}