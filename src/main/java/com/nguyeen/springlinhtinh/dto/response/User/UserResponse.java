package com.nguyeen.springlinhtinh.dto.response.User;

import com.nguyeen.springlinhtinh.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

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

    @Embedded
    Address address;

    String profileImage;
    boolean active;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
}