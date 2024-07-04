package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.RoleRequest;
import com.nguyeen.springlinhtinh.dto.response.RoleResponse;
import com.nguyeen.springlinhtinh.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // RoleRequest nhận vào 1 list String -> Mà trong Role lại là 1 list Permission
    // => phải tự map, ignore permisssions
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}