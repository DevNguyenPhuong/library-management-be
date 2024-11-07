package com.example.library.fine;

import com.example.library.constant.PaymentStatus;
import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.loan.Loan;
import com.example.library.loan.LoanRepository;
import com.example.library.patron.Patron;
import com.example.library.patron.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FineService {
    FineRepository fineRepository;
    PatronRepository patronRepository;
    LoanRepository loanRepository;
    FineMapper fineMapper;

    public FineResponse create(FineRequest fineRequest) {
        Patron patron = patronRepository.findById(fineRequest.getPatronId()).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));
        Loan loan = loanRepository.findById(fineRequest.getLoanId()).orElseThrow(() -> new AppException(ErrorCode.LOAN_NOT_FOUND));
        if(loan.getFine() != null)
            throw new AppException(ErrorCode.THIS_LOAN_HAVE_FINE);
        Fine fine = fineMapper.toFine(fineRequest);
        fine.setPatron(patron);
        fine.setLoan(loan);
        return fineMapper.toFineResponse(fineRepository.save(fine));
    }

    public FineResponse updateFine(String id, FineRequest request) {
        Fine fine = fineRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_FOUND));
        fineMapper.updateFine(fine, request);
        return fineMapper.toFineResponse(fineRepository.save(fine));
    }


    public Page<FineResponse> getFines( Pageable pageable) {
        Page<Fine> fines;
        fines = fineRepository.findAll(pageable);
        return fines.map(fineMapper::toFineResponse);
    }

    public List<FineResponse> getFinesByPatron(String patronId) {
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));
        List<Fine>  fines = fineRepository.findByPatron(patron);
        return fines.stream().map((fineMapper::toFineResponse)).toList();
    }

    public void deleteFine(String fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_FOUND));

        if (fine.getLoan() != null) {
            fine.getLoan().setFine(null);
            fine.setLoan(null);
        }
        fine.setPatron(null);

        fineRepository.delete(fine);
        fineRepository.flush();
    }
}
