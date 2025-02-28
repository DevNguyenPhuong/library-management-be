package com.example.library.loan;

import com.example.library.Libarian.Librarian;
import com.example.library.bookCopy.BookCopy;
import com.example.library.constant.LoanStatus;
import com.example.library.fine.Fine;
import com.example.library.patron.Patron;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    LocalDate loanDate;
    LocalDate dueDate;
    LocalDate returnDate;

    String bookId;

    @Enumerated(EnumType.STRING)
    LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    BookCopy bookCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    Librarian librarian;

    @ManyToOne(fetch = FetchType.LAZY)
    Patron patron;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Fine fine;
}
