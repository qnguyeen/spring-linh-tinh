package com.nguyeen.springlinhtinh.dto.response.User;

import com.nguyeen.springlinhtinh.entity.Address;
import com.nguyeen.springlinhtinh.enums.Gender;
import com.nguyeen.springlinhtinh.enums.UserStatus;
import com.nguyeen.springlinhtinh.validator.EnumValidator.EnumPatternConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String firstName;
    String lastName;
    String username;
    String phoneNumber;
    String email;
    Gender gender;
    Set<Address> addresses;
    String profileImage;
    UserStatus status;
    boolean active;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
}