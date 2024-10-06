package com.example.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String title;
    Integer publicationYear;
    Integer price;
    String publisher;
    Set<String> authors;
    Set<String> categories;
}
