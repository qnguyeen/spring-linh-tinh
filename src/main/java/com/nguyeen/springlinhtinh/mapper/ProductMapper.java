package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.Category.CategoryUpdateRequest;
import com.nguyeen.springlinhtinh.dto.request.Product.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "categories", ignore = true)
    Product toProduct(ProductRequest request);

    //MapStruct dung mapCategoriesToCategoryIds khai bao ben categoryMapper de anh xa cac field phuc tap
    @Mapping(target = "categoryIds", source = "categories")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "categories", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
