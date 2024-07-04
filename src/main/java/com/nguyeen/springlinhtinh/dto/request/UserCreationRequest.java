package com.nguyeen.springlinhtinh.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String fullName;
    String phoneNumber = "";

    @NotBlank(message = "Email không được để trống")
    @Email(message = "EMAIL_INVALID")
    private String email = "";

    @Size(min = 2, max = 50, message = "USERNAME_INVALID")
    String username;

    String address = "";

    @Size(min = 8, max = 100, message = "PASSWORD_INVALID")
    @NotBlank(message = "Password cannot be blank")
    String password = "";

    @NotBlank(message = "Retype password cannot be blank")
    String retypePassword = "";

    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;

    @NotNull(message = "Role ID is required")
    Long roleId;
}
