package com.nguyeen.springlinhtinh.dto.response;

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
    Long id;
    Long userId;
    String fullName;
    String phoneNumber;
    String email;
    String address;
    String note;

    @JsonProperty("order_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime orderDate;

    String status;
    double totalMoney;
    String shippingMethod = "";
    String shippingAddress = "";

    @JsonProperty("shipping_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    LocalDate shippingDate;

    String paymentMethod = "";
    List<OrderDetailResponse> orderDetails;
}
