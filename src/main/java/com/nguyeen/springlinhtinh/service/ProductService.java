package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.dto.request.Product.ProductImageRequest;
import com.nguyeen.springlinhtinh.dto.request.Product.ProductRequest;
import com.nguyeen.springlinhtinh.dto.response.Product.ProductResponse;
import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import com.nguyeen.springlinhtinh.entity.ProductImage;
import com.nguyeen.springlinhtinh.exception.AppException;
import com.nguyeen.springlinhtinh.exception.ErrorCode;
import com.nguyeen.springlinhtinh.mapper.ProductMapper;
import com.nguyeen.springlinhtinh.repository.CategoryRepository;
import com.nguyeen.springlinhtinh.repository.ProductImageRepository;
import com.nguyeen.springlinhtinh.repository.ProductRepository;
import com.nguyeen.springlinhtinh.repository.AuthenRepository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        Product product = productMapper.toProduct(request);

        Set<Category> categories = getValidCategories(request.getCategoryIds());
        product.setCategories(categories);

        try {
            product = productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        return productMapper.toProductResponse(product);
    }

    private Set<Category> getValidCategories(Set<Long> categoryIds) {
        Set<Category> categories = new HashSet<>();
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

            if (category.getParent() == null || category.getParent().getParent() == null) {
                throw new AppException(ErrorCode.CATEGORY_PARENT_NOT_VALID);
            }
            categories.add(category);
        }
        return categories;
    }

    public ProductImage createProductImage(Long productId, ProductImageRequest productImageRequest) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found" + productImageRequest.getProductId()));

        ProductImage newProductImage = ProductImage.builder()
                .product(product)
                .imageUrl(productImageRequest.getImageUrl())
                .build();

        int size = productImageRepository.findByProductId(productId).size();
        if(size > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new RuntimeException(
                    "Number of images must be <= " +ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        if (product.getThumbnail() == null ) {
            product.setThumbnail(newProductImage.getImageUrl());
        }
        productRepository.save(product);
        return productImageRepository.save(newProductImage);
    }

    public ProductResponse getProductById(Long productId) {
        return productRepository.getDetailProduct(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new RuntimeException("Cannot find product with id =" + productId));
    }

    public List<ProductResponse> getProductsByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .map(category1 -> category1.getParent().getParent())
                .filter(category1 -> category1 != null)//neu = null thi throw exception
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_PARENT_NOT_VALID));

//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
//
//        if (category.getParent() == null || category.getParent().getParent() == null) {
//            throw new AppException(ErrorCode.CATEGORY_PARENT_NOT_VALID);
//        }

        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    public Page<ProductResponse> getAllProducts(String keyword,
                                                Long categoryId, PageRequest pageRequest) {
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productsPage.map(productMapper::toProductResponse);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found" + productId));
        productMapper.updateProduct(product,request);

        Set<Category> categories = getValidCategories(request.getCategoryIds());
        product.setCategories(categories);
        return productMapper.toProductResponse(product);
    }
}
