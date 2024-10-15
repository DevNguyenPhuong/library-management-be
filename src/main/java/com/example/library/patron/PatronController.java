package com.example.library.patron;

import com.example.library.dto.book.BookResponse;
import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.loan.LoanResponse;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.loan.LoanService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patrons")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PatronController {
    PatronService patronService;
    LoanService loanService;

    @GetMapping
    public ApiResponse<Page<PatronResponse>> getAllPatron(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PatronResponse> patrons = patronService.getPatrons(query, pageable);

        return ApiResponse.<Page<PatronResponse>>builder()
                .result(patrons)
                .build();
    }

    @GetMapping("/{patronId}")
    ApiResponse<PatronResponse> getDetails(@PathVariable String patronId) {
        return ApiResponse.<PatronResponse>builder()
                .result(patronService.getDetails(patronId))
                .build();
    }

    @PostMapping
    ApiResponse<PatronResponse> createPatron(@RequestBody @Valid PatronRequest request) {
        return ApiResponse.<PatronResponse>builder()
                .result(patronService.create(request))
                .build();
    }

    @PutMapping("/{patronId}")
    ApiResponse<PatronResponse> updatePatron(@PathVariable @Valid String patronId, @RequestBody PatronRequest request) {
        return ApiResponse.<PatronResponse>builder()
                .result(patronService.updatePatron(patronId, request))
                .build();
    }

    @DeleteMapping("/{patronId}")
    ApiResponse<Void> delete(@PathVariable String patronId) {
        patronService.deletePatron(patronId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/{patronId}/loans")
    ApiResponse<List<LoanResponse>> getLoans(@PathVariable String patronId) {
        return ApiResponse.<List<LoanResponse>>builder()
                .result(loanService.getLoansByPatron(patronId))
                .build();
    }
}
