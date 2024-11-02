package com.example.library.fine;

import com.example.library.constant.LoanStatus;
import com.example.library.constant.PaymentStatus;
import com.example.library.loan.Loan;
import com.example.library.patron.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, String> {
    Integer countByPatronAndPaymentStatus(Patron patron, PaymentStatus Status);
}
