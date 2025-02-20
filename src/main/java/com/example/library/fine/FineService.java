package com.example.library.fine;

import com.example.library.constant.PaymentStatus;
import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
import com.example.library.email.MailService;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.loan.Loan;
import com.example.library.loan.LoanService;
import com.example.library.patron.Patron;
import com.example.library.patron.PatronService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FineService {
    FineRepository fineRepository;
    PatronService patronService;
    LoanService loanService;
    FineMapper fineMapper;
    MailService mailService;

    @PreAuthorize("hasRole('LIBRARIAN')")
    public FineResponse create(FineRequest fineRequest) {
        Patron patron = patronService.findPatronById(fineRequest.getPatronId());
        Loan loan = loanService.findLoanById(fineRequest.getLoanId());
        if(loan.getFine() != null)
            throw new AppException(ErrorCode.THIS_LOAN_HAVE_FINE);
        Fine fine = fineMapper.toFine(fineRequest);
        fine.setPatron(patron);
        fine.setLoan(loan);
        return fineMapper.toFineResponse(fineRepository.save(fine));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public FineResponse updateFine(String fineId, FineRequest request) {
        Fine fine = findFineById(fineId);
        fineMapper.updateFine(fine, request);
        return fineMapper.toFineResponse(fineRepository.save(fine));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public Page<FineResponse> getFines( Pageable pageable) {
        Page<Fine> fines;
        fines = fineRepository.findAll(pageable);
        return fines.map(fineMapper::toFineResponse);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<FineResponse> getFinesByPatron(String patronId) {
        Patron patron =  patronService.findPatronById(patronId);
        List<Fine>  fines = fineRepository.findByPatron(patron);
        return fines.stream().map((fineMapper::toFineResponse)).toList();
    }



  //  @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void processOverdueFines() {
        log.info("Starting automatic fine deduction process");
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<Fine> overdueFines = fineRepository.findOverdueFines(thirtyDaysAgo);

        for (Fine fine : overdueFines) {
            try {
                processAutomaticDeduction(fine);
            } catch (Exception e) {
                log.error("Error processing fine deduction for fine ID: {}", fine.getId(), e);
            }
        }

    }

    private void processAutomaticDeduction(Fine fine) {
        Patron patron = fine.getPatron();
        Integer deposit = patron.getDeposit();

        if (deposit >= fine.getAmount()) {
            int newDeposit = deposit - fine.getAmount();
            patron.setDeposit(newDeposit);
            fine.setPaymentStatus(PaymentStatus.PAID);
            fine.setPaymentDate(LocalDate.now());
            mailService.sendFineNotification(patron.getEmail(), patron.getName(), fine.getAmount(), newDeposit);
            fineRepository.save(fine);
            patronService.updatePatron(patron);
        }
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteFine(String fineId) {
        Fine fine = findFineById(fineId);

        if (fine.getLoan() != null) {
            fine.getLoan().setFine(null);
            fine.setLoan(null);
        }
        fine.setPatron(null);

        fineRepository.delete(fine);
        fineRepository.flush();
    }


    public Fine findFineById(String fineId){
        return fineRepository.findById(fineId)
                .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_FOUND));
    }
}
