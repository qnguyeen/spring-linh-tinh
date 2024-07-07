package com.nguyeen.springlinhtinh.dto.response.Product;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavoriteResponse {
    Long id;
    Long productId;
    String userId;
}
