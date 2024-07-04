package com.nguyeen.springlinhtinh.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
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
