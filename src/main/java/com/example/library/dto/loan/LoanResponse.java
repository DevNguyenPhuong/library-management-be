package com.example.library.dto.loan;

import com.example.library.bookCopy.BookCopy;
import com.example.library.constant.LoanStatus;
import com.example.library.dto.bookCopy.BookCopyResponse;
import com.example.library.dto.patron.PatronSimpleResponse;
import com.example.library.dto.user.UserSimpleResponse;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Builder
public class LoanResponse {
    String id;
    LocalDate loanDate;
    LocalDate returnDate;
    LocalDate dueDate;
    LoanStatus status;

    PatronSimpleResponse patron;
    BookCopyResponse bookCopy;
    UserSimpleResponse user;
}
