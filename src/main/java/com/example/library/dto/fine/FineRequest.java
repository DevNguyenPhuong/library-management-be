package com.example.library.dto.fine;

import com.example.library.constant.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineRequest {
    @NotNull(message = "FIELD_REQUIRED")
    @Positive(message = "POSITIVE_REQUIRED")
    Integer amount;
    String reason;
    @NotBlank(message = "FIELD_REQUIRED")
    String patronId;
    PaymentStatus paymentStatus;
    @NotNull(message = "FIELD_REQUIRED")
    LocalDate issueDate;
    LocalDate paymentDate;
    @NotBlank(message = "FIELD_REQUIRED")
    String loanId;
}
