package com.example.library.patron;

import com.example.library.constant.LoanStatus;
import com.example.library.constant.PatronStatus;
import com.example.library.constant.PredefinedRole;
import com.example.library.dto.patron.PatronCreationRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.dto.patron.PatronUpdateRequest;
import com.example.library.dto.user.UserCreationRequest;
import com.example.library.dto.user.UserResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.loan.Loan;
import com.example.library.loan.LoanRepository;
import com.example.library.loan.LoanService;
import com.example.library.user.User;
import com.example.library.user.UserRepository;
import com.example.library.user.UserService;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
    LoanService loanService;
    UserRepository userRepository;
    UserService userService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    public PatronResponse create(PatronCreationRequest request) {
        if(patronRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);

        if(patronRepository.existsByPhone(request.getPhone()))
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);

        if(patronRepository.existsByPersonalId(request.getPersonalId()))
            throw new AppException(ErrorCode.PERSONAL_ID_ALREADY_EXISTS);

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(patronRepository.existsByUser(user))
            throw new AppException(ErrorCode.PATRON_ALREADY_EXISTS);

        Patron patron = patronMapper.toPatron(request);
        patron.setDeposit(0);
        patron.setStatus(PatronStatus.INACTIVE);
        patron.setUser(user);

        try {
            patron = patronRepository.save(patron);
        }
        catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PATRON_ALREADY_EXISTS);
        }
        return patronMapper.toPatronResponse(patron);
    }

    public UserResponse register(UserCreationRequest request) {
        return userService.registerForPatron(request, PredefinedRole.PATRON);
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


    @PreAuthorize("hasRole('PATRON')")
    public PatronResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(!patronRepository.existsByUser(user))
            return PatronResponse.builder().build();

        Patron patron = patronRepository.findByUser(user).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));

        return patronMapper.toPatronResponse(patron);
    }

    @PreAuthorize("hasRole('PATRON')")
    public PatronResponse updateMe(PatronUpdateRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Patron currentPatron = patronRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));

        patronMapper.updatePatron(currentPatron, request);

        try {
            currentPatron = patronRepository.save(currentPatron);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        return patronMapper.toPatronResponse(currentPatron);
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    public PatronResponse updatePatron(String patronId, PatronUpdateRequest request) {
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
        userRepository.delete(patron.getUser());
    }


    public Patron updatePatron(Patron patron){
        return patronRepository.save(patron);
    }

    public Patron findPatronById(String id) {
        return patronRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));
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
