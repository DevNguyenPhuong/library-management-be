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
    @Min(1)
    int numberOfCopies;
    String bookId;
}
