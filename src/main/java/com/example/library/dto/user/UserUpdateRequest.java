package com.example.library.dto.user;

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

    @NotBlank(message = "FIELD_REQUIRED")
    String password;
    @NotBlank(message = "FIELD_REQUIRED")
    String firstName;
    @NotBlank(message = "FIELD_REQUIRED")
    String lastName;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
