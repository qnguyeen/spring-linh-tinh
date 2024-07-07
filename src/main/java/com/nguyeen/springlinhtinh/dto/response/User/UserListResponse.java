package com.nguyeen.springlinhtinh.dto.response.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListResponse {
    List<UserResponse> users;
    int totalPages;
}
