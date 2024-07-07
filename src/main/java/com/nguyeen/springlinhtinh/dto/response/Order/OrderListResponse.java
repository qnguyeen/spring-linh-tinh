package com.nguyeen.springlinhtinh.dto.response.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderListResponse {
    List<OrderResponse> orders;
    int totalPages;
}
