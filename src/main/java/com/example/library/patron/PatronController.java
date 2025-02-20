package com.example.library.patron;

import com.example.library.Shopping_session.ShoppingSessionService;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.ShoppingSession.ShoppingSessionResponse;
import com.example.library.dto.loan.LoanResponse;
import com.example.library.dto.patron.PatronCreationRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.dto.patron.PatronUpdateRequest;
import com.example.library.dto.user.UserCreationRequest;
import com.example.library.dto.user.UserResponse;
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
    private final ShoppingSessionService shoppingSessionService;

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

    @GetMapping("/my-info")
    ApiResponse<PatronResponse> getCurrentPatronInfo() {
        return ApiResponse.<PatronResponse>builder()
                .result(patronService.getMyInfo())
                .build();
    }


    @PutMapping("/update-me")
    ApiResponse<PatronResponse> updateMe(@RequestBody @Valid PatronUpdateRequest request) {
        return ApiResponse.<PatronResponse>builder()
                .result(patronService.updateMe(request))
                .build();
    }


    @PostMapping("/register")
    ApiResponse<UserResponse> createPatron(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(patronService.register(request))
                .build();
    }

    @PostMapping
    ApiResponse<PatronResponse> createPersonalInfo(@RequestBody @Valid PatronCreationRequest request) {
        return ApiResponse.<PatronResponse>builder()
                .result(patronService.create(request))
                .build();
    }


    @PutMapping("/{patronId}")
    ApiResponse<PatronResponse> updatePatron(@PathVariable @Valid String patronId, @RequestBody PatronUpdateRequest request) {
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

    @GetMapping("/{patronId}/shopping-session")
    ApiResponse<ShoppingSessionResponse> getShoppingSession(@PathVariable String patronId) {
        return ApiResponse.<ShoppingSessionResponse>builder()
                .result(shoppingSessionService.findByPatron(patronId))
                .build();
    }
}
