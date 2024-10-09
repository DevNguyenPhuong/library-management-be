package com.example.library.dto.patron;

import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import com.example.library.validator.DobConstraint;
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
    String name;
    String phone;
    Gender gender;
    PatronStatus status;
    Integer currentlyBorrowed;

    LocalDate membershipDate;

    @DobConstraint(min = 5, message = "INVALID_DATE")
    LocalDate dob;
}
