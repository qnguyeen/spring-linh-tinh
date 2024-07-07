package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.User.RoleRequest;
import com.nguyeen.springlinhtinh.dto.response.User.RoleResponse;
import com.nguyeen.springlinhtinh.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}