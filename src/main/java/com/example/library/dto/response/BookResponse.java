package com.example.library.dto.response;

import com.example.library.entity.Author;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String id;
    String title;
    Integer publicationYear;
    Integer price;
    PublisherResponse publisher;
    Set<AuthorResponse> authors;
    Set<CategoryResponse> categories;
}
