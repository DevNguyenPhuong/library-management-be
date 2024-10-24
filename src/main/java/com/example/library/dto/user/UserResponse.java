package com.example.library.dto.user;

import com.example.library.constant.Gender;
import com.example.library.dto.role.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String name;
    Gender gender; ;
    String phone;
    LocalDate dob;
    Set<RoleResponse> roles;
}
