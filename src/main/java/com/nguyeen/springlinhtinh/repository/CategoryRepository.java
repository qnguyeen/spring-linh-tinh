package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
