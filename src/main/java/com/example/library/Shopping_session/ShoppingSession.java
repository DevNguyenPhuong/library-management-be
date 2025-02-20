package com.example.library.Shopping_session;


import com.example.library.cart_item.CartItem;
import com.example.library.constant.ShoppingSessionStatus;
import com.example.library.patron.Patron;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ShoppingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    Patron patron;

    @Enumerated(EnumType.STRING)
    ShoppingSessionStatus status;

    @OneToMany(mappedBy = "shoppingSession")
    List<CartItem> cartItems;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;
}
