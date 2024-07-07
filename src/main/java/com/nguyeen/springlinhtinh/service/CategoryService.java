package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.dto.request.Category.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryUpdateRequest;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import com.nguyeen.springlinhtinh.exception.AppException;
import com.nguyeen.springlinhtinh.exception.ErrorCode;
import com.nguyeen.springlinhtinh.mapper.CategoryMapper;
import com.nguyeen.springlinhtinh.mapper.ProductMapper;
import com.nguyeen.springlinhtinh.repository.CategoryRepository;
import com.nguyeen.springlinhtinh.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Transactional
    public Category createCategory(CategoryRequest request) {
        boolean exists = categoryRepository.existsByNameAndParentId(request.getName(), request.getParentId());

        if (!exists) {
            Category category = categoryMapper.toCategory(request);

            if (request.getParentId() != null) {
                Category parentCategory = categoryRepository.findById(request.getParentId())
                        .orElseThrow(() -> new RuntimeException("Parent category not found"));

                category.setParent(parentCategory);
            }
            return categoryRepository.save(category);
        }else {
            throw new RuntimeException("Category with the same name and parent already exists");
        }

    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Transactional
    public Category updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryMapper.updateCategory(category, request);

        Long parentId = request.getParentId();
        if (parentId != null) {
            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.delete(category);
    }
}
