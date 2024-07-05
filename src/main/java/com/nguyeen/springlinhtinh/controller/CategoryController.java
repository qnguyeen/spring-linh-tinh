package com.nguyeen.springlinhtinh.controller;

import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.request.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.request.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.service.CategoryService;
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

}
