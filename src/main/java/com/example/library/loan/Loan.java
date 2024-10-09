package com.example.library.loan;

import com.example.library.constant.LoanStatus;
import com.example.library.fine.Fine;
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

    String bookCopyId;
    String  userId;
    String patronId;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Fine fine;
}
