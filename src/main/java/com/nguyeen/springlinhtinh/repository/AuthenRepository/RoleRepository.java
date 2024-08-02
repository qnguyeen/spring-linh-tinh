package com.nguyeen.springlinhtinh.repository.AuthenRepository;

import com.nguyeen.springlinhtinh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
