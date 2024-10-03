package com.example.library.entity;

import com.example.library.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    Integer amount;
    String reason;
    LocalDate issueDate;
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;
    LocalDate paymentDate;

    @OneToOne(mappedBy = "fine", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    Loan loan;
}
