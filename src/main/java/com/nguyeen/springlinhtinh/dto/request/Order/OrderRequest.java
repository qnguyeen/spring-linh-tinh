package com.nguyeen.springlinhtinh.dto.request.Order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("fullname")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    String phoneNumber;

    @JsonProperty("status")
    String status;

    String address;

    String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    Float totalMoney;

    @JsonProperty("shipping_method")
    String shippingMethod;

    @JsonProperty("shipping_address")
    String shippingAddress;

    @JsonProperty("shipping_date")
    LocalDate shippingDate;

    @JsonProperty("payment_method")
    String paymentMethod;

    @JsonProperty("coupon_code")
    String couponCode;

    @JsonProperty("cart_items")
    List<CartItemRequest> cartItems;
}
