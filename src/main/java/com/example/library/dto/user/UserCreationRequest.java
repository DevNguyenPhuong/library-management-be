package com.example.library.dto.user;

import com.example.library.constant.Gender;
import com.example.library.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest  {
    @Size(min = 5, message = "INVALID_USERNAME")
    String username;

    @Size(min = 5, message = "INVALID_PASSWORD")
    String password;

    List<String> roles;
}
