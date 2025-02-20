package com.example.library.dto.Librarian;

import com.example.library.constant.Gender;
import com.example.library.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibrarianCreationRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String name;
    @NotBlank(message = "FIELD_REQUIRED")
    String phone;

    @NotBlank(message = "FIELD_REQUIRED")
    String email;

    @NotBlank(message = "FIELD_REQUIRED")
    String address;

    Gender gender;

    @NotNull(message = "FIELD_REQUIRED")
    LocalDate membershipDate;

    @DobConstraint(min = 5, message = "INVALID_DATE")
    LocalDate dob;

}
