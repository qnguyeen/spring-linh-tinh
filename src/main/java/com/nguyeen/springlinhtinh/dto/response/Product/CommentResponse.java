package com.nguyeen.springlinhtinh.dto.response.Product;

import com.nguyeen.springlinhtinh.dto.BaseResponse;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    Long id;
    String content;
    UserResponse user;
    Long productId;
}
