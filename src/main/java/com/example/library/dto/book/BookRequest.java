package com.example.library.dto.book;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    @NotBlank(message = "FIELD_REQUIRED")
    String isbn;
    @NotBlank(message = "FIELD_REQUIRED")
    String publisher;

    @NotNull(message = "FIELD_REQUIRED")
    @Min(value = 1000, message = "PUBLICATION_YEAR_INVALID")
    @Max(value = 2024, message = "PUBLICATION_YEAR_INVALID")
    private Integer publicationYear;

    @NotNull(message = "FIELD_REQUIRED")
    @Positive(message = "POSITIVE_REQUIRED")
    private Integer price;

    @NotNull(message = "FIELD_REQUIRED")
    private Set<@NotBlank(message = "FIELD_REQUIRED") String> authors;

    @NotNull(message = "FIELD_REQUIRED")
    private Set<@NotBlank(message = "FIELD_REQUIRED") String> categories;


}
