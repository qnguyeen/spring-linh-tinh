package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.Category.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryUpdateRequest;
import com.nguyeen.springlinhtinh.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    //default method co body va k can override de su dung
    default Set<Long> mapCategoriesToCategoryIds(Set<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream().map(Category::getId).collect(Collectors.toSet());
    }

    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
