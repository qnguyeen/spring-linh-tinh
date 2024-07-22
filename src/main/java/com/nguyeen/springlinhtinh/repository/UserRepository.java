package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    //không cần phải viết những truy vấn dài dòng và lắp đi lặp lại
    //có thể sử dụng các method có sẵn trong JpaRepository
    //ví dụ ở duưới là tìm user theo username để xử lý user vừa tìm được đó (cập nhât, xoá, xem thông tin,...)
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
}
