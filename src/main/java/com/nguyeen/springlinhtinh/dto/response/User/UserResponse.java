package com.nguyeen.springlinhtinh.dto.response.User;

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
    String fullName;
    String userName;
    String phoneNumber;
    String address;
    String profileImage;
    boolean active;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
}
