package com.nguyeen.springlinhtinh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nguyeen.springlinhtinh.enums.Gender;
import com.nguyeen.springlinhtinh.enums.UserStatus;
import com.nguyeen.springlinhtinh.validator.DobValidator.DobConstraint;
import com.nguyeen.springlinhtinh.validator.EnumValidator.EnumPatternConstraint;
import com.nguyeen.springlinhtinh.validator.GenderValidator.GenderConstraint;
import com.nguyeen.springlinhtinh.validator.PhoneValidator.PhoneConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

import static com.nguyeen.springlinhtinh.enums.Gender.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "fullname",columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String fullName;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @Column(name = "phone_number", nullable = true)
    String phoneNumber;

    @GenderConstraint(anyOf = {MALE, FEMALE, OTHER})
    @Column(name = "gender", nullable = true)
    Gender gender;

    @Column(name = "email", nullable = true)
    String email;

    @Column(name = "address", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String address;

    @Column(name = "profile_image", length = 255)
    String profileImage;

    @NonNull
    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "status")
    @EnumPatternConstraint(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
    UserStatus status;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "facebook_account_id")
    int facebookAccountId;

    @Column(name = "google_account_id")
    int googleAccountId;

    @ManyToMany
    Set<Role> roles;
}
