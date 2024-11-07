package com.example.library.loan;

import com.example.library.constant.LoanStatus;
import com.example.library.patron.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String>, JpaSpecificationExecutor<Loan> {
    Integer countByPatronAndStatus(Patron patron, LoanStatus Status);
    List<Loan> findByPatronAndStatus(Patron patron, LoanStatus Status);

    boolean existsByPatronAndStatusAndDueDateBefore(
            Patron patron,
            LoanStatus status,
            LocalDate dateTime
    );


    @Query("SELECT l FROM Loan l " +
            "LEFT JOIN FETCH l.bookCopy bc " +
            "LEFT JOIN FETCH bc.book " +
            "LEFT JOIN FETCH l.user " +
            "LEFT JOIN FETCH l.patron " +
            "LEFT JOIN FETCH l.fine " +
            "WHERE l.patron.id = :patronId")
    List<Loan> findAllByPatronIdWithDetails(@Param("patronId") String patronId);

    boolean existsByPatronIdAndBookId(String patronId, String bookId);

    @Query("""
        SELECT COUNT(l) FROM Loan l
        WHERE l.status = :status
        AND l.loanDate BETWEEN :startDate AND :endDate
    """)
    long countByStatusAndDateRange(
            @Param("status") LoanStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT COUNT(l) FROM Loan l
        WHERE l.loanDate BETWEEN :startDate AND :endDate
        AND l.dueDate < :currentDate
        AND (l.returnDate IS NULL OR l.returnDate > l.dueDate)
    """)
    long countOverdueLoans(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("currentDate") LocalDate currentDate
    );
}
