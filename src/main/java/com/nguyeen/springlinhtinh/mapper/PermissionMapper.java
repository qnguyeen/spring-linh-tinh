package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.User.PermissionRequest;
import com.nguyeen.springlinhtinh.dto.response.User.PermissionResponse;
import com.nguyeen.springlinhtinh.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}