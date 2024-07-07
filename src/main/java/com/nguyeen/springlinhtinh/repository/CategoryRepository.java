package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long parentId);

    boolean existsByNameAndParentId(String categoryName, Long parentId);
}
