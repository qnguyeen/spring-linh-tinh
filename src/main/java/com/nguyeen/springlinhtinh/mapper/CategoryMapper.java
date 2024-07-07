package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.Category.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryUpdateRequest;
import com.nguyeen.springlinhtinh.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    //dùng map này khi muốn lấy only id hoặc string
//    default Set<String> mapCategoriesToCategoryNames(Set<Category> categories) {
//        if (categories == null) {
//            return null;
//        }
//        return categories.stream().map(Category::getName).collect(Collectors.toSet());
//    }

    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
