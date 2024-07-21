package com.nguyeen.springlinhtinh.controller;

import com.nguyeen.springlinhtinh.configuration.language.Translator;
import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.request.Category.CategoryRequest;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.service.CategoryService;
import com.nguyeen.springlinhtinh.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category Controller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;
    ProductService productService;

    //@PostMapping
    @Operation(method = "POST", summary = "Add new category", description = "Send a request via this API to create new user")
    @RequestMapping(method = POST, path = "/",headers = "apiKey=v1.0")
    ApiResponse<Category> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.createCategory(request))
                .message(Translator.toLocale("category.add.success"))
                .build();
    }

    @GetMapping
    ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.<List<Category>>builder()
                .result(categoryService.getAllCategories())
                .message(Translator.toLocale("category.get.all.success"))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<Category> getCategoryById(@PathVariable Long id) {
        return ApiResponse.<Category>builder()
                .result(categoryService.getCategoryById(id))
                .message(Translator.toLocale("category.get.success"))
                .build();
    }

    @GetMapping("/{categoryId}/products")
    ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByCategory(categoryId))
                .message(Translator.toLocale("category.get.products.success"))
                .build();
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<Category> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.updateCategory(categoryId, request))
                .message(Translator.toLocale("category.update.success"))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .message(Translator.toLocale("category.delete.success"))
                .build();
    }
}
