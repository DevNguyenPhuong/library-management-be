package com.example.library.book;

import com.example.library.author.Author;
import com.example.library.category.Category;
import com.example.library.publisher.Publisher;
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

    @Column(unique = true)
    String isbn;

    String title;
    Integer publicationYear;
    Integer price;

    @ManyToOne
    Publisher publisher;

    @ManyToMany
    Set<Category> categories;

    @ManyToMany
    Set<Author> authors;
}
