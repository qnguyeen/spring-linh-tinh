package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.User.UserCreationRequest;
import com.nguyeen.springlinhtinh.dto.request.User.UserUpdateRequest;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import com.nguyeen.springlinhtinh.entity.Address;
import com.nguyeen.springlinhtinh.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
