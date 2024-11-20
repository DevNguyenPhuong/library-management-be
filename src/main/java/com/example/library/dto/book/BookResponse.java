package com.example.library.dto.book;

import com.example.library.dto.author.AuthorSimpleResponse;
import com.example.library.dto.Category.CategorySimpleResponse;
import com.example.library.dto.publisher.PublisherSimpleResponse;
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
    String isbn;
    Integer publicationYear;
    Integer price;
    PublisherSimpleResponse publisher;
    Set<AuthorSimpleResponse> authors;
    Set<CategorySimpleResponse> categories;
    String imageUrl;
}
