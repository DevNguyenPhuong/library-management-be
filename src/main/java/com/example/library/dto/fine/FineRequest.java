package com.example.library.dto.fine;

import com.example.library.constant.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineRequest {
    Integer amount;
    String reason;
    LocalDate issueDate;
    PaymentStatus paymentStatus;
    LocalDate paymentDate;
    String loanId;
}
