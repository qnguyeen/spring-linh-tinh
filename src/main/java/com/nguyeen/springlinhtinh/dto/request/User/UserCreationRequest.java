package com.nguyeen.springlinhtinh.dto.request.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nguyeen.springlinhtinh.enums.Gender;
import com.nguyeen.springlinhtinh.validator.DobValidator.DobConstraint;
import com.nguyeen.springlinhtinh.validator.GenderValidator.GenderConstraint;
import com.nguyeen.springlinhtinh.validator.PhoneValidator.PhoneConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.nguyeen.springlinhtinh.enums.Gender.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    //các field ở đây tương ứng với thông tin cần nhập vào khi tạo mới một user
    //các thông tin này cần phải giống với Entity User trong db để mapping dữ liệu
    //có thể copy Entity User và đổi tên lại để tạo DTO cho nhanh (UserCreationRequest)
    String fullName;

    @PhoneConstraint(message = "Phone number invalid format")
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

    @DobConstraint(min = 18,message = "Date of birth invalid format")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date dateOfBirth;

    @GenderConstraint(anyOf = {MALE, FEMALE, OTHER})
    @Column(name = "gender", nullable = true)
    Gender gender;

    int facebookAccountId;
    int googleAccountId;

    @NotNull(message = "Role ID is required")
    Long roleId;
}
