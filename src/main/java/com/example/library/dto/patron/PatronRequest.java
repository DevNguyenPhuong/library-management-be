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
public class PatronRequest {
    @Size(min = 12, max = 12, message = "INVALID_PATRON_ID")
    String id;

    @NotBlank(message = "FIELD_REQUIRED")
    String name;
    @NotBlank(message = "FIELD_REQUIRED")
    String phone;
    @NotBlank(message = "FIELD_REQUIRED")
    Gender gender;
    @NotBlank(message = "FIELD_REQUIRED")
    PatronStatus status;

    @Size(min = 1, max = 5, message = "INVALID_BORROWED_BOOK")
    Integer currentlyBorrowed;

    @NotNull(message = "FIELD_REQUIRED")
    LocalDate membershipDate;

    @DobConstraint(min = 5, message = "INVALID_DATE")
    LocalDate dob;
}
