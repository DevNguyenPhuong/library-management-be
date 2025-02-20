package com.example.library.dto.CartItem;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemCreationRequest {
    String sessionId;
    String bookId;
    int quantity;
}
