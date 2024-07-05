package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.dto.request.CategoryRequest;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.exception.AppException;
import com.nguyeen.springlinhtinh.exception.ErrorCode;
import com.nguyeen.springlinhtinh.mapper.CategoryMapper;
import com.nguyeen.springlinhtinh.repository.CategoryRepository;
import com.nguyeen.springlinhtinh.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return categoryRepository.save(category);
        }catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
    }
}

