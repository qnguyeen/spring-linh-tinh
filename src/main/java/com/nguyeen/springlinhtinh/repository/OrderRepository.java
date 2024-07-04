package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {
    List<Order> findByUserId(Long userId);
}
