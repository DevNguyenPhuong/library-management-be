package com.example.library.user;

import com.example.library.Person.Person;
import com.example.library.constant.Gender;
import com.example.library.role.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "_user")
public class User extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;


    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany
    private Set<Role> roles;
}

