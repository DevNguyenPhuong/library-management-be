package com.example.library.entity;

import com.example.library.constant.Gender;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    LocalDate dob;
    @Enumerated(EnumType.STRING)
    Gender gender;
    Integer currentlyBorrowed;
    LocalDate membershipDate;
}
