package com.example.library.book;

import com.example.library.dto.book.BookRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.bookCopy.BookCopyResponse;
import com.example.library.dto.book.BookResponse;
import com.example.library.bookCopy.BookCopyService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    BookService bookService;
    BookCopyService bookCopyService;

    @GetMapping
    public ApiResponse<Page<BookResponse>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponse> books = bookService.getBooks(query, pageable);

        return ApiResponse.<Page<BookResponse>>builder()
                .result(books)
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
    ApiResponse<Page<BookCopyResponse>> getCopies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query,
            @PathVariable String bookId)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookCopyResponse> bookCopies = bookCopyService.getBookCopiesByBookId(bookId, pageable, query);
        return ApiResponse.<Page<BookCopyResponse>>builder()
                .result(bookCopies)
                .build();
    }

}
