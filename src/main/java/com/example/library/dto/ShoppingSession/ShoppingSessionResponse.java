package com.example.library.dto.ShoppingSession;

import com.example.library.cart_item.CartItem;
import com.example.library.constant.ShoppingSessionStatus;
import com.example.library.dto.CartItem.CartItemResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingSessionResponse {
    String id;
    ShoppingSessionStatus status;
    LocalDateTime created_at;
    List<CartItemResponse> cartItems;
}
