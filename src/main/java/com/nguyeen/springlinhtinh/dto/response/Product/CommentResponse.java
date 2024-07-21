package com.nguyeen.springlinhtinh.dto.response.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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

    @JsonFormat(pattern = "dd-MM-yyyy 'T 'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy 'T 'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime updatedAt;
}
