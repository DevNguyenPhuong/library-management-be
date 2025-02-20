package com.example.library.dto.Librarian;

import com.example.library.constant.Gender;
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
public class LibrarianResponse {
    String id;
    String name;
    String phone;
    String email;
    String address;
    Gender gender;
    LocalDate membershipDate;
    LocalDate dob;
}
