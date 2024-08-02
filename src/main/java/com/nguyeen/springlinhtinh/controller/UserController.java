package com.nguyeen.springlinhtinh.controller;

import com.nguyeen.springlinhtinh.dto.ApiResponse;
import com.nguyeen.springlinhtinh.dto.PageResponse;
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
import org.springframework.data.domain.Pageable;
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
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/sort")
    ApiResponse<PageResponse<UserResponse>> getUsersWithSortBy(@RequestParam(defaultValue = "0",required = false) int page,
                                             @Min(2)@RequestParam(defaultValue = "3",required = false) int size,
                                             @RequestParam(required = false) String sortBy){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getUsersWithSortBy(page, size,sortBy))
                .build();
    }

    @GetMapping("/search")
    ApiResponse<PageResponse<UserResponse>> getUsersWithSortByColumnAndSearch(@RequestParam(defaultValue = "0",required = false) int page,
                                                               @Min(2)@RequestParam(defaultValue = "3",required = false) int size,
                                                               @RequestParam(required = false) String search,
                                                               @RequestParam(required = false) String sortBy
                                                               ){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getUsersWithSortByColumnAndSearch(page, size,search,sortBy))
                .build();
    }

    @GetMapping("/criteria-search")
    ApiResponse<PageResponse<UserResponse>> advancedSearchByCriteria(@RequestParam(defaultValue = "0",required = false) int page,
                                                                              @Min(2)@RequestParam(defaultValue = "3",required = false) int size,
                                                                              @RequestParam(required = false) String sortBy,
                                                                              @RequestParam(required = false) String address,
                                                                              @RequestParam(required = false) String... search//=String[] search
    ){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.advancedSearchByCriteria(page, size,sortBy, address,search))
                .build();
    }

    @GetMapping("/advanced-search")
    ApiResponse<PageResponse<UserResponse>> advanceSearchWithSpecifications(Pageable pageable,
                                                                            @RequestParam(required = false) String[] user,
                                                                            @RequestParam(required = false) String[] address
    ){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.advanceSearchWithSpecifications(pageable, user, address))
                .build();
    }

    @GetMapping("/sorts")
    ApiResponse<PageResponse<UserResponse>> getUsersWithSortsBy(@RequestParam(defaultValue = "0",required = false) int page,
                                                                @Min(2)@RequestParam(defaultValue = "3",required = false) int size,
                                                                @RequestParam(required = false) String... sorts){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getUsersWithSortsByMultipleColumn(page, size, sorts))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<UserResponse>> getUsers(@RequestParam(defaultValue = "0",required = false) int page,
                                             @Min(2)@RequestParam(defaultValue = "3",required = false) int size){
        return ApiResponse.<PageResponse<UserResponse>>builder()
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
