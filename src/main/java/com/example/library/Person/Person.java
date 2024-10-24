package com.example.library.Person;

import com.example.library.constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    protected String name;

    @Column(unique = true)
    protected String phone;

    protected LocalDate dob;

    @Enumerated(EnumType.STRING)
    protected Gender gender;
}