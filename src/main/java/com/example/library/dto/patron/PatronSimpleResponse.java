package com.example.library.dto.patron;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatronSimpleResponse {
    String id;
    String name;
}
