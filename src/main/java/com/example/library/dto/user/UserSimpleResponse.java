package com.example.library.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSimpleResponse {
    String id;
    String username;
}
