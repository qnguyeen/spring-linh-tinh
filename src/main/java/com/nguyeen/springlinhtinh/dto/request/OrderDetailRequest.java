package com.nguyeen.springlinhtinh.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    @JsonProperty("order_id")
    @Min(value=1, message = "Order's ID must be > 0")
    Long orderId;

    @Min(value=1, message = "Product's ID must be > 0")
    @JsonProperty("product_id")
    Long productId;

    @Min(value=0, message = "Product's ID must be >= 0")
    Float price;

    @Min(value=1, message = "number_of_products must be >= 1")
    @JsonProperty("number_of_products")
    int numberOfProducts;

    @Min(value=0, message = "total_money must be >= 0")
    @JsonProperty("total_money")
    Float totalMoney;

    String color;
}
