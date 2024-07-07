package com.nguyeen.springlinhtinh.dto.response.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    String id;
    String orderId;
    Long productId;
    String productName;
    String thumbnail;
    Float price;
    int numberOfProducts;
    Float totalMoney;
    String color;
}
