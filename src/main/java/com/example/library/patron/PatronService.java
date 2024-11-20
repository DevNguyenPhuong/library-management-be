package com.example.library.patron;

import com.example.library.constant.LoanStatus;
import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.loan.Loan;
import com.example.library.loan.LoanRepository;
import com.example.library.loan.LoanService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class PatronService {
    PatronRepository patronRepository;
    PatronMapper patronMapper;
    LoanRepository loanRepository;
    private final LoanService loanService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @PreAuthorize("hasRole('LIBRARIAN')")
    public PatronResponse create(PatronRequest request) {
        if(patronRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);

        if(patronRepository.existsByPhone(request.getPhone()))
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);

        Patron patron = patronMapper.toPatron(request);

        patron.setDeposit(650000);

        try {
            patron = patronRepository.save(patron);
        }
        catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        return patronMapper.toPatronResponse(patron);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<PatronResponse> getAll() {
        var patrons  = patronRepository.findAll();
        return patrons.stream().map(patronMapper::toPatronResponse).toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public Page<PatronResponse> getPatrons(String query, Pageable pageable) {
        Page<Patron> patrons;
        if (query == null || query.isEmpty()) {
            patrons = patronRepository.findAll(pageable);
        } else {
            patrons = patronRepository.findByNameContainingIgnoreCaseOrIdContainingIgnoreCase(query, query, pageable);
        }
        return patrons.map(this::toPatronResponse);
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    public PatronResponse getDetails(String id){
        var patron = patronRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return this.toPatronResponse(patron);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public PatronResponse updatePatron(String patronId, PatronRequest request) {
        Patron patron = findPatronById(patronId);
        patronMapper.updatePatron(patron, request);
        return patronMapper.toPatronResponse(updatePatron(patron));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deletePatron(String patronId) {
        Patron patron = findPatronById(patronId);
        List<Loan> loans = loanRepository.findAllByPatronId(patronId);

        List<String> loanIds = new ArrayList<>();
        for (Loan loan : loans) {
            loanIds.add(loan.getId());
        }

        for (String loanId : loanIds) {
            loanService.delete(loanId);
        }

        patronRepository.deleteById(patronId);
    }


    public Patron updatePatron(Patron patron){
        return patronRepository.save(patron);
    }

    public Patron findPatronById(String id) {
        return patronRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private PatronResponse toPatronResponse(Patron patron) {
        PatronResponse response = new PatronResponse();
        response.setId(patron.getId());
        response.setName(patron.getName());
        response.setPhone(patron.getPhone());
        response.setDeposit(patron.getDeposit());
        response.setGender(patron.getGender());
        response.setStatus(patron.getStatus());
        response.setDob(patron.getDob());
        response.setEmail(patron.getEmail());
        response.setMembershipDate(patron.getMembershipDate());

        Integer currentlyBorrowed = loanRepository.countByPatronAndStatus(patron, LoanStatus.BORROWED);
        response.setCurrentlyBorrowed(currentlyBorrowed);

        return response;
    }
}
