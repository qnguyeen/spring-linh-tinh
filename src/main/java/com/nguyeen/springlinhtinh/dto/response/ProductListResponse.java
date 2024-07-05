package com.nguyeen.springlinhtinh.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListResponse {
    List<ProductResponse> products;
    int totalPages;
}
