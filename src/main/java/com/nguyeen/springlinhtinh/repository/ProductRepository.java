package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.Category;
import com.nguyeen.springlinhtinh.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    List<Product> findByCategory(Category category);
}
