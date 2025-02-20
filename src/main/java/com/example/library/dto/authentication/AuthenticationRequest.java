package com.example.library.dto.authentication;

import com.example.library.constant.PredefinedRole;
import com.example.library.role.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    String username;
    String password;
    PredefinedRole role;
}

