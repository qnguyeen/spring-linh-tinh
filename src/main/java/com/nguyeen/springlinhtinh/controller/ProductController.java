package com.nguyeen.springlinhtinh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.request.Product.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductListResponse;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Product;
import com.nguyeen.springlinhtinh.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {
    ProductService productService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .message("Create new product successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long productId){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(productId))
                .message("Get product successfully")
                .build();
    }


}
