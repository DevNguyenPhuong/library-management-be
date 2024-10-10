package com.example.library.patron;

import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Patron {
    @Id
    String id;

    String name;
    @Column(unique = true)
    String phone;
    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Enumerated(EnumType.STRING)
    PatronStatus status;

    Integer currentlyBorrowed;
    LocalDate membershipDate;
}
