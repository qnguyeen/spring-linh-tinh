package com.nguyeen.springlinhtinh.repository.AuthenRepository;

import com.nguyeen.springlinhtinh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);

}
