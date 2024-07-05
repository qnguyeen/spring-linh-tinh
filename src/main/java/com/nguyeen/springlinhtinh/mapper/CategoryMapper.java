package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.ProductRequest;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
}
