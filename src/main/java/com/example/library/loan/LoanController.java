package com.example.library.loan;

import com.example.library.dto.loan.LoanRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.loan.LoanResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoanController {
    LoanService loanService;

    @PostMapping
    public ApiResponse<LoanResponse> loan(@RequestBody LoanRequest request) {
        return ApiResponse.<LoanResponse>builder()
                .result(loanService.create(request))
                .build();
    }

    @PutMapping("/{loanId}")
    public ApiResponse<LoanResponse> updateLoan(@PathVariable String loanId, @RequestBody LoanRequest request) {
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
