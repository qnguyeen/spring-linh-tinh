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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    ApiResponse<Category> createProduct(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.createCategory(request))
                .message("Create new category successfully")
                .build();
    }

}
