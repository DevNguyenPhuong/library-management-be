package com.example.library.dto.patron;

import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import com.example.library.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatronUpdateRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    @Size(min = 12, max = 12, message = "INVALID_PATRON_ID")
    String personalId;

    @NotBlank(message = "FIELD_REQUIRED")
    String name;
    @NotBlank(message = "FIELD_REQUIRED")
    String phone;

    @NotBlank(message = "FIELD_REQUIRED")
    String email;

    Gender gender;

    PatronStatus status;

    LocalDate membershipDate;

    @DobConstraint(min = 5, message = "INVALID_DATE")
    LocalDate dob;
}
