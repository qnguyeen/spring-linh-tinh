package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.dto.request.ProductImageRequest;
import com.nguyeen.springlinhtinh.dto.request.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import com.nguyeen.springlinhtinh.entity.ProductImage;
import com.nguyeen.springlinhtinh.exception.AppException;
import com.nguyeen.springlinhtinh.exception.ErrorCode;
import com.nguyeen.springlinhtinh.mapper.ProductMapper;
import com.nguyeen.springlinhtinh.repository.CategoryRepository;
import com.nguyeen.springlinhtinh.repository.ProductImageRepository;
import com.nguyeen.springlinhtinh.repository.ProductRepository;
import com.nguyeen.springlinhtinh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository
                .getReferenceById(request.getCategoryId());//tự throw exception

        Product product = productMapper.toProduct(request);
        product.setCategory(category);
        try {
            product = productRepository.save(product);
        }catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        return productMapper.toProductResponse(product);
    }
    public ProductImage createProductImage(
            Long productId,
            ProductImageRequest productImageRequest)
    {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found" + productImageRequest.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(product)
                .imageUrl(productImageRequest.getImageUrl())
                .build();

        int size = productImageRepository.findByProductId(productId).size();
        if(size > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new RuntimeException(
                    "Number of images must be <= "
                            +ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        if (product.getThumbnail() == null ) {
            product.setThumbnail(newProductImage.getImageUrl());
        }
        productRepository.save(product);
        return productImageRepository.save(newProductImage);
    }

}
