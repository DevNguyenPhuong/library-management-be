package com.example.library.dto.loan;


import com.example.library.constant.LoanStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Builder
public class LoanRequest {

    LocalDate loanDate;

    LocalDate returnDate;

    LocalDate dueDate;


    LoanStatus status;

    @NotBlank(message = "FIELD_REQUIRED")
    String patronId;
    @NotBlank(message = "FIELD_REQUIRED")
    String bookCopyId;
    @NotBlank(message = "FIELD_REQUIRED")
    String userId;
}
