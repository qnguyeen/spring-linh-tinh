package com.nguyeen.springlinhtinh.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "provider", nullable = false, length = 20)
    String provider;

    @Column(name = "provider_id", nullable = false, length = 50)
    String providerId;

    @Column(name = "name",length = 150)
    String name;

    @Column(name = "email", length = 150)
    String email;

}

