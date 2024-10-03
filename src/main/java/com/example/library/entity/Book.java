package com.example.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;
    Integer publicationYear;
    Integer price;
    Integer totalCopies;
    Integer availableCopies;

    @ManyToOne
    Publisher  publisher;

    @ManyToMany
    Set<Category> categories;

    @ManyToMany
    Set<Author> authors;
}
