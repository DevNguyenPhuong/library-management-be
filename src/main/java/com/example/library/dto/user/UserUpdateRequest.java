package com.example.library.dto.user;

import com.example.library.constant.Gender;
import com.example.library.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    Gender gender;


    String name;

    String phone;

    @DobConstraint(min = 5, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;

    // for password
    String newPassword;
    String oldPassword;
    String confirmPassword;
}
