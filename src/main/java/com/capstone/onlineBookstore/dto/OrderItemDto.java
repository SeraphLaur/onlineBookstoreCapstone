package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;

/**
 * The type Order item dto.
 */
public class OrderItemDto {
    /**
     * The Id.
     */
    public Long id;
    /**
     * The Book id.
     */
    public Long bookId;
    /**
     * The Book title.
     */
    public String bookTitle;
    /**
     * The Quantity.
     */
    public Integer quantity;
    /**
     * The Unit price.
     */
    public BigDecimal unitPrice;
    /**
     * The Line total.
     */
    public BigDecimal lineTotal;
}