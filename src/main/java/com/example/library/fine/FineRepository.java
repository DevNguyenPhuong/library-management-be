package com.example.library.fine;

import com.example.library.constant.PaymentStatus;
import com.example.library.patron.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, String> {
    Integer countByPatronAndPaymentStatus(Patron patron, PaymentStatus Status);

    List<Fine> findByPatron(Patron patron);

    @Query("SELECT f FROM Fine f WHERE f.paymentStatus = 'UNPAID' " +
            "AND f.issueDate <= :date")
    List<Fine> findOverdueFines(@Param("date") LocalDate date);
}
