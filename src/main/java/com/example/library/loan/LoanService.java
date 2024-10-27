package com.example.library.loan;

import com.example.library.bookCopy.BookCopy;
import com.example.library.dto.loan.LoanRequest;
import com.example.library.dto.loan.LoanResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.bookCopy.BookCopyRepository;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoanService {
    LoanRepository loanRepository;
    LoanMapper loanMapper;
    PatronRepository patronRepository;
    BookCopyRepository bookCopyRepository;
    UserRepository userRepository;

    @Transactional
    public LoanResponse create(LoanRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(request.getBookCopyId()).orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_FOUND));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Patron patron = patronRepository.findById(request.getPatronId()).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));

        Loan loan = loanMapper.toLoan(request);

        loan.setBookCopy(bookCopy);
        loan.setUser(user);
        loan.setPatron(patron);

        return loanMapper.toLoanResponse(loanRepository.save(loan));
    }

    @Transactional
    public LoanResponse update(LoanRequest request, String loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new AppException(ErrorCode.LOAN_NOT_FOUND));
        loanMapper.updateLoan(loan, request);
        return loanMapper.toLoanResponse(loanRepository.save(loan));
    }

    public List<LoanResponse> getLoansByPatron(String patronId) {
        return loanRepository.findAllByPatronIdWithDetails(patronId)
                .stream()
                .map(loanMapper::toLoanResponse)
                .toList();
    }

    public void delete(String loanId) {
        loanRepository.deleteById(loanId);
    }

    public Page<LoanResponse> getLoans(Pageable pageable) {
        Page<Loan> loans = loanRepository.findAll(pageable);
        return loans.map(loanMapper::toLoanResponse);
    }
}
