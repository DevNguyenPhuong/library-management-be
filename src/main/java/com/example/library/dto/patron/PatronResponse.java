package com.example.library.dto.patron;

import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatronResponse {
    String id;
    String name;
    String phone;
    String email;
    String personalId;
    Gender gender;
    Integer deposit;
    PatronStatus status;
    Integer currentlyBorrowed;
    LocalDate membershipDate;
    LocalDate dob;
}
