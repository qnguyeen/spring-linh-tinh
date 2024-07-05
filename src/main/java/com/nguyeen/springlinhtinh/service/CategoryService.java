package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.dto.request.CategoryRequest;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.mapper.CategoryMapper;
import com.nguyeen.springlinhtinh.repository.CategoryRepository;
import com.nguyeen.springlinhtinh.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    CategoryMapper categoryMapper;

    public Category createCategory(CategoryRequest request) {

        Category category = categoryMapper.toCategory(request);

        return categoryRepository.save(category);
    }
}

