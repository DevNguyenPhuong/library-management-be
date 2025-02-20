package com.example.library.cart_item;

import com.example.library.Shopping_session.ShoppingSession;
import com.example.library.book.Book;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    int quantity;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime created_at;

    @OneToOne
    Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    ShoppingSession shoppingSession;
}
