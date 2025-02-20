package com.example.library.Libarian;


import com.example.library.constant.Gender;
import com.example.library.constant.LibrarianStatus;
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
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    String name;

    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String address;

    LibrarianStatus status;

    @Column(unique = true)
    String phone;

    @Column(unique = true)
    String email;

    @OneToOne
    User user;
}
