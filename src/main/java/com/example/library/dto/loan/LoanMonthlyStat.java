package com.example.library.dto.loan;


import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Builder
public class LoanMonthlyStat {
     long borrowed;
     long returned;
     long overdue;
}
