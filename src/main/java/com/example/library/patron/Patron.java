package com.example.library.patron;

import com.example.library.constant.Gender;
import com.example.library.constant.PatronStatus;
import com.example.library.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @Column(nullable = false, unique = true)
    String personalId;

    String name;

    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false)
    int deposit;

    @Column(unique = true)
    String phone;

    @Column(unique = true)
    String email;

    LocalDate membershipDate;

    @Enumerated(EnumType.STRING)
    PatronStatus status;

    @OneToOne
    User user;
}
