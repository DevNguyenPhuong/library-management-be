package com.example.library.dto.CartItem;

import com.example.library.book.Book;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    String id;
    int quantity;
    Book book;
    LocalDateTime created_at;
}
