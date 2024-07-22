package com.nguyeen.springlinhtinh.controller;

import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.request.User.UserCreationRequest;
import com.nguyeen.springlinhtinh.dto.request.User.UserUpdateRequest;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import com.nguyeen.springlinhtinh.entity.User;
import com.nguyeen.springlinhtinh.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        //thông tin nhập vào đang ở dưới dạng JSON -> cần tới @RequestBody
        // @RequestBody : chuyển JSON trong file UserCreationRequest thành đối tượng java
        // Thông tin được lưu tạm thời trong request, sau đó được chuyển vào Entity User thông qua UserMapper trong phần UserService

        //ApiResponse ni là class để định dạng dữ liệu trả về cho client
        return ApiResponse.<UserResponse>builder()
                //khúc ni gọi hàm tạo mới user từ UserService(chuyển UserCreationRequest xuống để xử lý)
                .result(userService.createUser(request))
                .build();
    }
// dữ liệu đi lần lượt qua controller -> servive -> repository -> database
// controller xử lý request post, update, delete,... từ client, nhận dữ liệu từ DTO request -> chuyển qua service
// service xử lý logic, xử lý dữ liệu, gọi repository để lưu dữ liệu vào database
// repository thao tác thẳng tới db, như tìm user, lưu user vào table

    //đây là code nếu không có DTO
//    @PostMapping
//    public ApiResponse<UserResponse> createUser(
//            @RequestParam String fullName,
//            @RequestParam String phoneNumber,
//            @RequestParam String email,
//            @RequestParam String username,
//            @RequestParam String address,
//            @RequestParam String password,
//            @RequestParam String retypePassword,
//            @RequestParam Date dateOfBirth,
//            @RequestParam Gender gender,
//            @RequestParam int facebookAccountId,
//            @RequestParam int googleAccountId,
//            @RequestParam Long roleId) {
//
//        UserCreationRequest request = UserCreationRequest.builder()
//                .fullName(fullName)
//                .phoneNumber(phoneNumber)
//                .email(email)
//                .username(username)
//                .address(address)
//                .password(password)
//                .retypePassword(retypePassword)
//                .dateOfBirth(dateOfBirth)
//                .gender(gender)
//                .facebookAccountId(facebookAccountId)
//                .googleAccountId(googleAccountId)
//                .roleId(roleId)
//                .build();
//
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.createUser(request))
//                .build();
//    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(@RequestParam(defaultValue = "1",required = false) int page,
                                             @Min(2)@RequestParam(defaultValue = "20",required = false) int size){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers(page, size))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }
}
