package com.example.library.dto.loan;


import com.example.library.constant.LoanStatus;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Builder
public class LoanRequest {
    String id;
    LocalDate loanDate;
    LocalDate returnDate;
    LocalDate dueDate;
    LoanStatus status;
    String patronId;
    String bookCopyId;
    String userId;
}
