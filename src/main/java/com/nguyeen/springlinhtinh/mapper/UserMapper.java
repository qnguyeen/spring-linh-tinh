package com.nguyeen.springlinhtinh.mapper;

import com.nguyeen.springlinhtinh.dto.request.User.UserCreationRequest;
import com.nguyeen.springlinhtinh.dto.request.User.UserUpdateRequest;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import com.nguyeen.springlinhtinh.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //Mapper là một interface, nó giúp chuyển đổi dữ liệu từ DTO sang Entity và ngược lại
    //tự động kiểm tra các field cùng tên với nhau để chuyển dữ liệu qua
    //mục đích làm gọn code và dễ chỉnh sửa sau này nếu muốn thêm sửa field mới

    //Map từ DTO request sang Entity User
    //dùng ở phần service
    User toUser(UserCreationRequest request);

    //Map từ Entity User sang DTO response
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
