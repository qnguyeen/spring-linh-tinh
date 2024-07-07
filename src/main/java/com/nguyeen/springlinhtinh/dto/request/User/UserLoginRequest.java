package com.nguyeen.springlinhtinh.dto.request.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginRequest {
    String phoneNumber = "";

    @NotBlank(message = "Email không được để trống")
    @Email(message = "EMAIL_INVALID")
    private String email = "";

    @Size(min = 8, max = 100, message = "PASSWORD_INVALID")
    @NotBlank(message = "Password cannot be blank")
    String password = "";
}
