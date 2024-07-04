package com.nguyeen.springlinhtinh.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "fullname",columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String fullName;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @Column(name = "phone_number", nullable = true)
    String phoneNumber;

    @Column(name = "email", nullable = true)
    String email;

    @Column(name = "address", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String address;

    @Column(name = "profile_image", length = 255)
    String profileImage;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "is_active")
    boolean active;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "facebook_account_id")
    int facebookAccountId;

    @Column(name = "google_account_id")
    int googleAccountId;

    @ManyToMany
    Set<Role> roles;
}
