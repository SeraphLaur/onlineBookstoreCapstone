package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * The type Order dto.
 */
public class OrderDto {
    /**
     * The Id.
     */
    public Long id;
    /**
     * The User id.
     */
    public Long userId;
    /**
     * The Status.
     */
    public String status;
    /**
     * The Total.
     */
    public BigDecimal total;
    /**
     * The Items.
     */
    public List<OrderItemDto> items;
}


//
//package com.capstone.onlineBookstore.dto;
//
//// For status updates
//public class UpdateOrderStatusRequest {
//    public String status;
//}
