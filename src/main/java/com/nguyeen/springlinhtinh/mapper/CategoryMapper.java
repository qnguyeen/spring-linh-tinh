package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.Category.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryUpdateRequest;
import com.nguyeen.springlinhtinh.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    @Mapping(target = "children", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
