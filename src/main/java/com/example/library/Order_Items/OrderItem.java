package com.example.library.Order_Items;

import com.example.library.Order_details.OrderDetails;
import com.example.library.book.Book;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    OrderDetails orderDetails;
}
