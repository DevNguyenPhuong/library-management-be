package com.example.library.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String>, JpaSpecificationExecutor<Loan> {
    List<Loan> findAllByPatronId(String patronId);

    @Query("SELECT l FROM Loan l " +
            "LEFT JOIN FETCH l.bookCopy bc " +
            "LEFT JOIN FETCH bc.book " +
            "LEFT JOIN FETCH l.user " +
            "LEFT JOIN FETCH l.patron " +
            "WHERE l.patron.id = :patronId")
    List<Loan> findAllByPatronIdWithDetails(@Param("patronId") String patronId);

}
