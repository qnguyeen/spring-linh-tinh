package com.nguyeen.springlinhtinh.dto.response.Product;

import com.nguyeen.springlinhtinh.dto.response.Category.CategoryResponse;
import com.nguyeen.springlinhtinh.entity.ProductImage;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    Set<CategoryResponse> categories;
}
