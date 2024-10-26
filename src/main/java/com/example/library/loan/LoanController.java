package com.example.library.loan;

import com.example.library.dto.loan.LoanRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.loan.LoanResponse;
import com.example.library.dto.patron.PatronResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoanController {
    LoanService loanService;

    @GetMapping
    public ApiResponse<Page<LoanResponse>> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<LoanResponse> loans = loanService.getLoans(pageable);

        return ApiResponse.<Page<LoanResponse>>builder()
                .result(loans)
                .build();
    }

    @PostMapping
    public ApiResponse<LoanResponse> creatLoan(@RequestBody @Valid LoanRequest request) {
        return ApiResponse.<LoanResponse>builder()
                .result(loanService.create(request))
                .build();
    }

    @PutMapping("/{loanId}")
    public ApiResponse<LoanResponse> updateLoan(@PathVariable @Valid String loanId, @RequestBody LoanRequest request) {
        return  ApiResponse.<LoanResponse>builder()
                .result(loanService.update(request, loanId))
                .build();
    }

    @DeleteMapping("/{loanId}")
    public ApiResponse<Void> loan(@PathVariable String loanId) {
        loanService.delete(loanId);
        return ApiResponse.<Void>builder().build();
    }
}
