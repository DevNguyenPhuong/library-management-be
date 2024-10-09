package com.example.library.dto.bookCopy;

import com.example.library.constant.BookCopyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyResponse {
    String id;
    BookCopyStatus status;
}
