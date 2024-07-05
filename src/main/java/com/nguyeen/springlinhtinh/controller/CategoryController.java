package com.nguyeen.springlinhtinh.controller;

import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryUpdateRequest;
import com.nguyeen.springlinhtinh.dto.response.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.service.CategoryService;
import com.nguyeen.springlinhtinh.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;
    ProductService productService;

    @PostMapping
    ApiResponse<Category> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.createCategory(request))
                .message("Create new category successfully")
                .build();
    }

    @GetMapping
    ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.<List<Category>>builder()
                .result(categoryService.getAllCategories())
                .message("Fetch all categories successfully")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<Category> getCategoryById(@PathVariable Long id) {
        return ApiResponse.<Category>builder()
                .result(categoryService.getCategoryById(id))
                .message("Fetch category successfully")
                .build();
    }

    @GetMapping("/{categoryId}/products")
    ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByCategory(categoryId))
                .message("Fetch products by category successfully")
                .build();
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<Category> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryUpdateRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.updateCategory(categoryId, request))
                .message("Update category successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .message("Delete category successfully")
                .build();
    }
}
