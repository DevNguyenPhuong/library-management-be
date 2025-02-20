package com.example.library.dto.ShoppingSession;

import com.example.library.constant.ShoppingSessionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingSessionUpdateRequest {
    ShoppingSessionStatus status;
}
