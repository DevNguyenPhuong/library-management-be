package com.example.library.dto.ShoppingSession;

import com.example.library.constant.ShoppingSessionStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingSessionCreationRequest {
    ShoppingSessionStatus status;

    @NotBlank(message = "NOT_BLANK")
    String patronId;
}
