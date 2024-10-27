package com.example.library.dto.authentication;

import com.example.library.constant.Gender;
import com.example.library.role.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    boolean authenticated;
    String id;
    String username;
    String name;
    String phone;
    LocalDate dob;
    Gender gender;
    Set<Role> roles;
    Long expiresIn;
}
