package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.ProductRequest;
import com.nguyeen.springlinhtinh.dto.request.RoleRequest;
import com.nguyeen.springlinhtinh.dto.response.ProductResponse;
import com.nguyeen.springlinhtinh.dto.response.RoleResponse;
import com.nguyeen.springlinhtinh.entity.Product;
import com.nguyeen.springlinhtinh.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);
}
