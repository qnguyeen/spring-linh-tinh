package com.nguyeen.springlinhtinh.dto.response.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String userId;
    String fullName;
    String phoneNumber;
    String email;
    String address;
    String note;

    @JsonProperty("order_date")
    @JsonFormat(pattern = "dd-MM-yyyy 'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime orderDate;

    String status;
    double totalMoney;
    String shippingMethod = "";
    String shippingAddress = "";

    @JsonProperty("shipping_date")
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    LocalDate shippingDate;

    String paymentMethod = "";
    List<OrderDetailResponse> orderDetails;
}
