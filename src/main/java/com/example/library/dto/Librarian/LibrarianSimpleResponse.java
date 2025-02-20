package com.example.library.dto.Librarian;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibrarianSimpleResponse {
    String id;
    String name;
}
