package com.example.library.dto.bookCopy;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreationBookCopiesRequest {
    @Min(value = 1, message = "INVALID_COPIES")
    int numberOfCopies;
    String bookId;
}
