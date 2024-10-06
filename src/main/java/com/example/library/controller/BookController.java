package com.example.library.controller;

import com.example.library.dto.request.AuthorRequest;
import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.AuthorResponse;
import com.example.library.dto.response.BookResponse;
import com.example.library.service.BookService;
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
    ApiResponse<BookResponse> create(@RequestBody BookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.create(request))
                .build();
    }

    @PutMapping("/{bookID}")
    ApiResponse<BookResponse> update(@RequestBody BookRequest request, @PathVariable String bookID) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.update(request, bookID))
                .build();
    }

    @DeleteMapping("/{bookID}")
    ApiResponse<Void> delete(@PathVariable String bookID) {
        bookService.delete(bookID);
        return ApiResponse.<Void>builder().build();
    }

}
