package com.example.library.bookCopy;


import com.example.library.dto.Category.CategoryResponse;
import com.example.library.dto.bookCopy.BookCopyRequest;
import com.example.library.dto.bookCopy.CreationBookCopiesRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.bookCopy.BookCopyResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookCopyController {
    BookCopyService bookCopyService;

    @PostMapping
    ApiResponse<List<BookCopyResponse>> createCopies(@RequestBody @Valid CreationBookCopiesRequest request) {
        return ApiResponse.<List<BookCopyResponse>>builder()
                .result(bookCopyService.createCopies(request))
                .build();
    }

    @PutMapping("/{bookCopyId}")
    ApiResponse <BookCopyResponse> updateCopies(@PathVariable("bookCopyId") String bookCopyId, @RequestBody @Valid BookCopyRequest request) {
        return ApiResponse.<BookCopyResponse>builder()
                .result(bookCopyService.updateBookCopy(bookCopyId, request))
                .build();
    }

    @GetMapping("/{bookCopiesId}")
    ApiResponse<BookCopyResponse> getDetails(@PathVariable String bookCopiesId) {
        return ApiResponse.<BookCopyResponse>builder()
                .result(bookCopyService.getDetails(bookCopiesId))
                .build();
    }

    @DeleteMapping("/{bookCopyId}")
    ApiResponse<Void> deleteByBookCopyId(@PathVariable String bookCopyId) {
        bookCopyService.delete(bookCopyId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping
    ApiResponse<List<BookCopyResponse>> getAllCopies() {
        return   ApiResponse.<List<BookCopyResponse>>builder()
                .result(bookCopyService.getBooksCopy())
                .build();
    }
}
