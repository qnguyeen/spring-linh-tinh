package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.Token;
import com.nguyeen.springlinhtinh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}
