package com.example.library.fine;

import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
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
@RequestMapping("/fines")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FineController {
    FineService fineService;

    @GetMapping
    public ApiResponse<Page<FineResponse>> getFines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FineResponse> fines = fineService.getFines(pageable);
        return ApiResponse.<Page<FineResponse>>builder()
                .result(fines)
                .build();
    }


    @PostMapping
    ApiResponse<FineResponse> createFine(@RequestBody @Valid FineRequest request) {
        return ApiResponse.<FineResponse>builder()
                .result(fineService.create(request))
                .build();
    }

    @PutMapping("/{fineId}")
    ApiResponse<FineResponse> updatePatron(@PathVariable @Valid String fineId, @RequestBody FineRequest request) {
        return ApiResponse.<FineResponse>builder()
                .result(fineService.updateFine(fineId, request))
                .build();
    }

    @DeleteMapping("/{fineId}")
    ApiResponse<Void> delete(@PathVariable String fineId) {
        fineService.deleteFine(fineId);
        return ApiResponse.<Void>builder().build();
    }
}
