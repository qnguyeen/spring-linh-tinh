package com.nguyeen.springlinhtinh.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    Long orderId;
    Long productId;
    String productName;
    String thumbnail;
    Float price;
    int numberOfProducts;
    Float totalMoney;
    String color;
}
