package com.nguyeen.springlinhtinh.dto.request.User;

import com.nguyeen.springlinhtinh.validator.EnumValidator.EnumValueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String fullName;
    String phoneNumber = "";

    @NotBlank(message = "Email không được để trống")
    @Email(message = "EMAIL_INVALID")
    private String email = "";

    String address = "";

    @Size(min = 8, max = 100, message = "PASSWORD_INVALID")
    @NotBlank(message = "Password cannot be blank")
    String password = "";

    @NotBlank(message = "Retype password cannot be blank")
    String retypePassword = "";

    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;

    List<String> roles;
}
