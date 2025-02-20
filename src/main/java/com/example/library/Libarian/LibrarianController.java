package com.example.library.Libarian;

import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.Librarian.LibrarianResponse;
import com.example.library.dto.Librarian.LibrarianUpdateRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.dto.patron.PatronUpdateRequest;
import com.example.library.dto.user.UserCreationRequest;
import com.example.library.dto.user.UserResponse;
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
@RequestMapping("/librarians")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class LibrarianController {
    LibrarianService librarianService;

    @PostMapping
    ApiResponse<UserResponse> createLibrarian(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(librarianService.createLibrarianAccount(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<LibrarianResponse>> getAllLibrarians(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {

        Pageable pageable = PageRequest.of(page, size);
        Page<LibrarianResponse> librarians = librarianService.getLibrarians(query, pageable);

        return ApiResponse.<Page<LibrarianResponse>>builder()
                .result(librarians)
                .build();
    }

    @DeleteMapping("/{librarianId}")
    public ApiResponse<Void> deleteLibrarian(@PathVariable String librarianId) {
        librarianService.deleteLibrarian(librarianId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/my-info")
    ApiResponse<LibrarianResponse> getCurrentLibrarianInfo() {
        return ApiResponse.<LibrarianResponse>builder()
                .result(librarianService.getMyInfo())
                .build();
    }


    @PutMapping("/update-me")
    ApiResponse<LibrarianResponse> updateMe(@RequestBody @Valid LibrarianUpdateRequest request) {
        return ApiResponse.<LibrarianResponse>builder()
                .result(librarianService.updateMe(request))
                .build();
    }
}
