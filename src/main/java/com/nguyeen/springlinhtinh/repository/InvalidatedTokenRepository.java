package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
