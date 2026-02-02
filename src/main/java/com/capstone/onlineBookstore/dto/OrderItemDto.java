package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;

public class OrderItemDto {
    public Long id;
    public Long bookId;
    public String bookTitle;
    public Integer quantity;
    public BigDecimal unitPrice;
    public BigDecimal lineTotal;
}