package com.capstone.onlineBookstore.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class OrderDto {
    public Long id;
    public Long userId;
    public String status;
    public BigDecimal total;
    public List<OrderItemDto> items;
}


//
//package com.capstone.onlineBookstore.dto;
//
//// For status updates
//public class UpdateOrderStatusRequest {
//    public String status;
//}
