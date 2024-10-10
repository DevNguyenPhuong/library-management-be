package com.example.library.book;

import com.example.library.dto.book.BookRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.bookCopy.BookCopyResponse;
import com.example.library.dto.book.BookResponse;
import com.example.library.bookCopy.BookCopyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    BookService bookService;
    BookCopyService bookCopyService;

    @GetMapping
    ApiResponse<List<BookResponse>> getAll() {
        return ApiResponse.<List<BookResponse>>builder()
                .result(bookService.getAll())
                .build();
    }

    @GetMapping("/{bookID}")
    ApiResponse<BookResponse> getDetails(@PathVariable String bookID) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getDetails(bookID))
                .build();
    }

    @PostMapping
    ApiResponse<BookResponse> create(@RequestBody @Valid BookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.create(request))
                .build();
    }

    @PutMapping("/{bookID}")
    ApiResponse<BookResponse> update(@RequestBody @Valid BookRequest request, @PathVariable String bookID) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.update(request, bookID))
                .build();
    }

    @DeleteMapping("/{bookID}")
    ApiResponse<Void> delete(@PathVariable String bookID) {
        bookService.delete(bookID);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/{bookId}/copies")
    ApiResponse<List<BookCopyResponse>> getCopies(@PathVariable String bookId) {
        return ApiResponse.<List<BookCopyResponse>>builder()
                .result(bookCopyService.getAllByBookId(bookId))
                .build();
    }

}
