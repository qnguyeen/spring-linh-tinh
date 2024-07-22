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
    // cái DTO response này có mục đích trả về thông tin lấy từ bảng User nhưng có vài field không muốn
    //trả về như password, thông tin cá nhân, ...
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
