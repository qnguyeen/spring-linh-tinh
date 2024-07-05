package com.nguyeen.springlinhtinh.dto.response;

import com.nguyeen.springlinhtinh.entity.ProductImage;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    Float price;
    String thumbnail;
    String description;
    int totalPages;
    List<ProductImage> productImages = new ArrayList<>();
    List<CommentResponse> comments = new ArrayList<>();
    List<FavoriteResponse> favorites = new ArrayList<>();
    Long quantity;
}
