package com.example.library.patron;

import com.example.library.Person.Person;
import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Patron extends Person {
    @Id
    protected String id;

    @Enumerated(EnumType.STRING)
    PatronStatus status;

    @Column(unique = true)
    String email;

    @Column(nullable = false)
    int deposit;

    LocalDate membershipDate;
}
