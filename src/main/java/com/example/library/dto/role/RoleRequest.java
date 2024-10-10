package com.example.library.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String name;
    @NotBlank(message = "FIELD_REQUIRED")
    String description;
    Set<String> permissions;
}
