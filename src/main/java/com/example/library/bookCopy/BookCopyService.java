package com.example.library.bookCopy;

import com.example.library.constant.BookCopyStatus;
import com.example.library.dto.bookCopy.CreationBookCopiesRequest;
import com.example.library.dto.bookCopy.BookCopyResponse;
import com.example.library.book.Book;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.book.BookRepository;
import com.example.library.fine.Fine;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookCopyService {
    BookCopyRepository bookCopyRepository;
    BookRepository bookRepository;
    BookCopyMapper bookCopyMapper;

    public Page<BookCopyResponse> getBookCopiesByBookId(String bookId, Pageable pageable) {
        Page<BookCopy> bookCopies = bookCopyRepository.findByBookId(bookId, pageable);
        return bookCopies.map(bookCopyMapper::toBookCopyResponse);
    }

    public List<BookCopyResponse> getBooksCopy() {
        var bookCopies = bookCopyRepository.findAll();
        return bookCopies.stream().map(bookCopyMapper::toBookCopyResponse).toList();
    }

    public void delete(String bookCopyId) {
        bookCopyRepository.deleteById(bookCopyId);
    }

    public List<BookCopyResponse> createCopies(CreationBookCopiesRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        List<BookCopy> newCopies = new ArrayList<>();
        for (int i = 0; i < request.getNumberOfCopies(); i++) {
            BookCopy newCopy = BookCopy.builder()
                    .book(book)
                    .status(BookCopyStatus.AVAILABLE) // Assuming new copies are initially available
                    .build();
            newCopies.add(newCopy);
        }

        List<BookCopy> savedCopies = bookCopyRepository.saveAll(newCopies);
        return savedCopies.stream()
                .map(bookCopyMapper::toBookCopyResponse)
                .toList();
    }
}
