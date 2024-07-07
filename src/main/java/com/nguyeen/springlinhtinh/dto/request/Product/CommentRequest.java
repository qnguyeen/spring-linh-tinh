package com.nguyeen.springlinhtinh.dto.request.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("content")
    private String content;
}
