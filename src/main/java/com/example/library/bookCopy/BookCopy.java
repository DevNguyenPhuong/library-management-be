package com.example.library.bookCopy;

import com.example.library.Shopping_session.ShoppingSession;
import com.example.library.book.Book;
import com.example.library.constant.BookCopyStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    BookCopyStatus status;

    @ManyToOne
    Book book;
}
