package com.nguyeen.springlinhtinh.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @JsonProperty("email")
    String email;

    @JsonProperty("username")
    String username;

    @NotBlank(message = "Password cannot be blank")
    String password;
}
