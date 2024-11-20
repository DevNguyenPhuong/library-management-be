package com.example.library.dto.patron;

import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import com.example.library.validator.DobConstraint;
import jakarta.validation.constraints.*;
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
    String email;

    Gender gender;

    Integer deposit;

    PatronStatus status;

    @Min(value = 0, message = "INVALID_BORROWED_BOOK")
    @Max(value = 5, message = "INVALID_BORROWED_BOOK")
    Integer currentlyBorrowed;

    @NotNull(message = "FIELD_REQUIRED")
    LocalDate membershipDate;

    @DobConstraint(min = 5, message = "INVALID_DATE")
    LocalDate dob;
}
