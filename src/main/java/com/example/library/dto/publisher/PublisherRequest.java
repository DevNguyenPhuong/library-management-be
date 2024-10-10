package com.example.library.dto.publisher;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherRequest {
    @NotBlank(message = "NOT_BLANK")
    String name;
    @NotBlank(message = "NOT_BLANK")
    String address;
}
