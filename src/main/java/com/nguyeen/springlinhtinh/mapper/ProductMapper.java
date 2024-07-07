package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.Product.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.Category.CategoryResponse;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "categories", ignore = true)
    Product toProduct(ProductRequest request);

    //làm rõ method được dùng để ánh xạ cùng
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategories")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "categories", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequest request);

    //mapstruct có thể tìm được ánh xạ giữa category và categoryResponse nhưng nên viết hàm này để rõ ràng
    @Named("mapCategories")
    default Set<CategoryResponse> mapCategories(Set<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toSet());
    }
}
