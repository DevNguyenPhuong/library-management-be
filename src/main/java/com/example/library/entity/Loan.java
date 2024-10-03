package com.example.library.entity;

import com.example.library.constant.LoanStatus;
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

    @Enumerated(EnumType.STRING)
    LoanStatus status;

    @ManyToOne
    Book  book;

    @ManyToOne
    User  user;

    @ManyToOne
    Patron  patron;

    @OneToOne
    @JoinColumn(nullable = true)
    private Fine fine;
}
