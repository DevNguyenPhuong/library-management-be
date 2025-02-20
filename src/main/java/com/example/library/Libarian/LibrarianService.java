package com.example.library.Libarian;

import com.example.library.constant.LibrarianStatus;
import com.example.library.constant.PredefinedRole;
import com.example.library.dto.Librarian.LibrarianCreationRequest;
import com.example.library.dto.Librarian.LibrarianResponse;
import com.example.library.dto.Librarian.LibrarianUpdateRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.dto.patron.PatronUpdateRequest;
import com.example.library.dto.user.UserCreationRequest;
import com.example.library.dto.user.UserResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.loan.Loan;
import com.example.library.loan.LoanRepository;
import com.example.library.loan.LoanService;
import com.example.library.patron.Patron;
import com.example.library.user.User;
import com.example.library.user.UserRepository;
import com.example.library.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LibrarianService {
    LibrarianRepository librarianRepository;
    UserRepository userRepository;
    LibrarianMapper librarianMapper;
    UserService userService;
    LoanRepository loanRepository;
    LoanService loanService;

    public LibrarianResponse createLibrarianInfo(LibrarianCreationRequest request) {
        if(librarianRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);

        if(librarianRepository.existsByPhone(request.getPhone()))
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(librarianRepository.existsByUser(user))
            throw new AppException(ErrorCode.LIBRARIAN_ALREADY_EXISTS);

        Librarian librarian = librarianMapper.toLibrarian(request);
        librarian.setStatus(LibrarianStatus.ACTIVE);

        try {
            librarian = librarianRepository.save(librarian);
        }
        catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.LIBRARIAN_ALREADY_EXISTS);
        }
        return librarianMapper.toLibrarianResponse(librarian);
    }

    public UserResponse createLibrarianAccount(UserCreationRequest request) {
        UserResponse userResponse = userService.registerForPatron(request, PredefinedRole.LIBRARIAN);
        LibrarianResponse librarianResponse = createEmptyLibrarianInfo(userResponse.getId());
        return userResponse;
    }

    private LibrarianResponse createEmptyLibrarianInfo(String userId) {
        // Create a new Librarian object with null or default values
        LibrarianCreationRequest request = new LibrarianCreationRequest();
        Librarian librarian = librarianMapper.toLibrarian(request);
        librarian.setStatus(LibrarianStatus.INACTIVE);

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        librarian.setUser(user);

        try {
            librarian = librarianRepository.save(librarian);
        }
        catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.LIBRARIAN_ALREADY_EXISTS);
        }

        return librarianMapper.toLibrarianResponse(librarian);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public LibrarianResponse updateMe(LibrarianUpdateRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Librarian currentLibrarian = librarianRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.LIBRARIAN_NOT_FOUND));

        librarianMapper.updateLibrarian(currentLibrarian, request);
        currentLibrarian.setStatus(LibrarianStatus.ACTIVE);

        try {
            currentLibrarian = librarianRepository.save(currentLibrarian);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        return librarianMapper.toLibrarianResponse(currentLibrarian);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public LibrarianResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(!librarianRepository.existsByUser(user))
            return LibrarianResponse.builder().build();

        Librarian librarian = librarianRepository.findByUser(user).orElseThrow(() -> new AppException(ErrorCode.LIBRARIAN_NOT_FOUND));

        return librarianMapper.toLibrarianResponse(librarian);
    }



    public Page<LibrarianResponse> getLibrarians(String query, Pageable pageable) {
        Page<Librarian> librarians;
        if (query == null || query.isEmpty()) {
            librarians = librarianRepository.findAll(pageable);
        } else {
            librarians = librarianRepository.findByNameContainingIgnoreCaseOrIdContainingIgnoreCase(query, query, pageable);
        }
        return librarians.map(librarianMapper::toLibrarianResponse);
    }

    public void deleteLibrarian(String librarianId) {
        Librarian librarian  = findById(librarianId);
        User user = librarian.getUser();

        List<Loan> loans = loanRepository.findAllByLibrarianId(librarianId);

        List<String> loanIds = new ArrayList<>();
        for (Loan loan : loans) {
            loanIds.add(loan.getId());
        }

        for (String loanId : loanIds) {
            loanService.delete(loanId);
        }

        librarianRepository.deleteById(librarianId);
        userRepository.delete(librarian.getUser());

    }

    public Librarian findById(String librarianId) {
        return librarianRepository.findById(librarianId).orElseThrow(() -> new AppException(ErrorCode.LIBRARIAN_NOT_FOUND));
    }

}
