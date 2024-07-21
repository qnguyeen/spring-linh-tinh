package com.nguyeen.springlinhtinh.controller;

import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.request.Product.ProductImageRequest;
import com.nguyeen.springlinhtinh.dto.request.Product.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.ProductImage;
import com.nguyeen.springlinhtinh.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import com.nguyeen.springlinhtinh.utils.FileUtils; // Import lớp FileUtils
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
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

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files) throws Exception {

        ProductResponse existingProduct = productService.getProductById(productId);
        files = files == null ? new ArrayList<MultipartFile>() : files;
        if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            return ApiResponse.<ProductResponse>builder()
                    .message("Maximum images per product is " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT)
                    .build();
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if(file.getSize() == 0) {
                continue;
            }
            // Kiểm tra kích thước file và định dạng
            if(file.getSize() > 10 * 1024 * 1024) {
                return ApiResponse.<ProductResponse>builder()
                        .message("Maximum file size is 10MB")
                        .build();
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                return ApiResponse.<ProductResponse>builder()
                        .message("Only image files are allowed")
                        .build();
            }
            // Lưu file và cập nhật thumbnail trong DTO
            String filename = FileUtils.storeFile(file);
            //lưu vào đối tượng product trong DB
            ProductImage productImage = productService.createProductImage(
                    existingProduct.getId(),
                    ProductImageRequest.builder()
                            .imageUrl(filename)
                            .build()
            );
            productImages.add(productImage);
        }

        return ApiResponse.<ProductResponse>builder()
                .result(existingProduct)
                .message("Upload images successfully")
                .build();
    }


}
