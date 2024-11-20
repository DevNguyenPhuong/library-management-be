package com.example.library.book;

import com.example.library.dto.book.BookRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.bookCopy.BookCopyResponse;
import com.example.library.dto.book.BookResponse;
import com.example.library.bookCopy.BookCopyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
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
    public ApiResponse<Page<BookResponse>> getBooks(
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
    ApiResponse<BookResponse> getBook(@PathVariable String bookID) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBook(bookID))
                .build();
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<BookResponse> createBook(
            @RequestPart("book") @Valid BookRequest bookRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ApiResponse.<BookResponse>builder()
                .result(bookService.createBook(bookRequest, image))
                .build();
    }

    @PutMapping(
            value = "/{bookId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<BookResponse> updateBook(
            @RequestPart("book") @Valid BookRequest bookRequest,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable String bookId

    ) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.updateBook(bookRequest, bookId, image))
                .build();
    }

    @DeleteMapping("/{bookID}")
    ApiResponse<Void> deleteBook(@PathVariable String bookID) {
        bookService.deleteBook(bookID);
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
