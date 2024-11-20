package com.example.library.loan;

import com.example.library.bookCopy.BookCopy;
import com.example.library.bookCopy.BookCopyService;
import com.example.library.constant.BookCopyStatus;
import com.example.library.constant.LoanStatus;
import com.example.library.constant.PatronStatus;
import com.example.library.constant.PaymentStatus;
import com.example.library.dto.loan.LoanMonthlyStat;
import com.example.library.dto.loan.LoanRequest;
import com.example.library.dto.loan.LoanResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.bookCopy.BookCopyRepository;
import com.example.library.fine.FineRepository;
import com.example.library.patron.Patron;
import com.example.library.patron.PatronRepository;
import com.example.library.user.User;
import com.example.library.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Slf4j
public class LoanService {
    LoanRepository loanRepository;
    LoanMapper loanMapper;
    PatronRepository patronRepository;
    BookCopyRepository bookCopyRepository;
    UserRepository userRepository;
    FineRepository fineRepository;
    BookCopyService bookCopyService;

    @PreAuthorize("hasRole('LIBRARIAN')")
    public LoanResponse create(LoanRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(request.getBookCopyId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_FOUND));
        if(bookCopy.getStatus() != BookCopyStatus.AVAILABLE)
            throw new AppException(ErrorCode.BOOK_COPY_NOT_AVAILABLE);

        Patron patron = patronRepository.findById(request.getPatronId()).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));
        if(patron.getStatus() != PatronStatus.ACTIVE)
            throw new AppException(ErrorCode.PATRON_NOT_ACTIVE);


        checkLoanEligibility(patron, bookCopy);

        // Create and save the loan
        Loan loan = createLoan(request, bookCopy, patron);
        return loanMapper.toLoanResponse(loan);
    }

    private void checkLoanEligibility(Patron patron, BookCopy bookCopy) {
        // Check if patron already borrowed this book
        if (loanRepository.existsByPatronIdAndBookId(patron.getId(), bookCopy.getBook().getId())) {
            throw new AppException(ErrorCode.PATRON_HAS_ALREADY_BORROWED_THIS_BOOK);
        }

        // Check maximum borrowed books limit
        final int MAX_BORROWED_BOOKS = 5;
        if (loanRepository.countByPatronAndStatus(patron, LoanStatus.BORROWED) >= MAX_BORROWED_BOOKS) {
            throw new AppException(ErrorCode.PATRON_REACHED_LOAN_LIMIT);
        }

        // Check for outstanding fines
        if (fineRepository.countByPatronAndPaymentStatus(patron, PaymentStatus.UNPAID) != 0) {
            throw new AppException(ErrorCode.PATRON_HAS_OUTSTANDING_FINES);
        }

        // Check membership expiration
        LocalDate currentDate = LocalDate.now();
        LocalDate membershipEndDate = patron.getMembershipDate().plusYears(1);
        if (currentDate.isAfter(membershipEndDate)) {
            throw new AppException(ErrorCode.PATRON_MEMBERSHIP_EXPIRED);
        }

        final int LOW_DEPOSIT = 100000;
        if(patron.getDeposit() <= LOW_DEPOSIT){
            throw new AppException(ErrorCode.PATRON_DEPOSIT_IS_TOO_LOW);
        }

        // Check for overdue loans
        if (loanRepository.existsByPatronAndStatusAndDueDateBefore(
                patron,
                LoanStatus.BORROWED,
                LocalDate.now())) {
            throw new AppException(ErrorCode.PATRON_HAS_OVERDUE_LOANS);
        }
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    private Loan createLoan(LoanRequest request, BookCopy bookCopy, Patron patron) {
        Loan loan = loanMapper.toLoan(request);
        bookCopy.setStatus(BookCopyStatus.BORROWED);
        bookCopyRepository.save(bookCopy);

        // Set relationships and save the loan
        loan.setBookId(bookCopy.getBook().getId());
        loan.setBookCopy(bookCopy);
        loan.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        loan.setPatron(patron);
        return loanRepository.save(loan);
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    public LoanResponse update(LoanRequest request, String loanId) {
        Loan loan = findLoanById(loanId);
        if(request.getStatus() == LoanStatus.RETURNED) {
            BookCopy bookCopy = bookCopyRepository.findById(request.getBookCopyId()).orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_FOUND));
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepository.save(bookCopy);
        }

        loanMapper.updateLoan(loan, request);
        return loanMapper.toLoanResponse(loanRepository.save(loan));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<LoanResponse> getLoansByPatron(String patronId) {
        return loanRepository.findAllByPatronIdWithDetails(patronId)
                .stream()
                .map(loanMapper::toLoanResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public LoanMonthlyStat getLoanStatsByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        LocalDate now = LocalDate.now();

        // Count currently borrowed (not returned and not overdue)
        long borrowedCount = loanRepository.countByStatusAndDateRange(LoanStatus.BORROWED, startDate, endDate);

        // Count returned
        long returnedCount = loanRepository.countByStatusAndDateRange(LoanStatus.RETURNED, startDate, endDate);

        // Count overdue (due date has passed and not returned)
        long overdueCount = loanRepository.countOverdueLoans(startDate, endDate, now);
        return new LoanMonthlyStat(borrowedCount, returnedCount, overdueCount);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void delete(String loanId) {
        Loan loan = findLoanById(loanId);
        BookCopy bookCopy = bookCopyService.findById(loan.getBookCopy().getId());
        bookCopy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopyRepository.save(bookCopy);

        loanRepository.deleteById(loanId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public Page<LoanResponse> getLoans(Pageable pageable) {
        Page<Loan> loans = loanRepository.findAll(pageable);
        return loans.map(loanMapper::toLoanResponse);
    }

    public  Loan findLoanById(String loanId) {
        return  loanRepository.findById(loanId).orElseThrow(() -> new AppException(ErrorCode.LOAN_NOT_FOUND));
    }
}
