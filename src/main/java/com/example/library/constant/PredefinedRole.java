package com.example.library.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PredefinedRole {
    KEEPER("KEEPER"),
    ADMIN("ADMIN"),
    USER("USER"),
    LIBRARIAN("LIBRARIAN"),
    PATRON("PATRON");

    private final String roleName;

}