package com.example.library.Person;

import com.example.library.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Person {
    String name;

    @Column(unique = true)
    String phone;

    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;
}