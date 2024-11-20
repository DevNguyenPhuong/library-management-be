package com.example.library.bookCopy;

import com.example.library.book.BookService;
import com.example.library.category.Category;
import com.example.library.constant.BookCopyStatus;
import com.example.library.dto.Category.CategoryResponse;
import com.example.library.dto.bookCopy.BookCopyRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    BookService bookService;
    BookCopyMapper bookCopyMapper;

    public Page<BookCopyResponse> getBookCopiesByBookId(String bookId, Pageable pageable, String query) {
        Page<BookCopy> bookCopies;
        if(query == null || query.isEmpty()) {
            bookCopies = bookCopyRepository.findByBookId(bookId, pageable);
        }else {
            bookCopies = bookCopyRepository.findByBookIdAndIdContainingIgnoreCase(bookId, query, pageable);
        }
        return bookCopies.map(bookCopyMapper::toBookCopyResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public List<BookCopyResponse> getBooksCopy() {
        var bookCopies = bookCopyRepository.findAll();
        return bookCopies.stream().map(bookCopyMapper::toBookCopyResponse).toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void delete(String bookCopyId) {
        BookCopy bookCopy = findById(bookCopyId);
        if(bookCopy.getStatus() == BookCopyStatus.BORROWED)
            throw new AppException(ErrorCode.BOOK_COPY_IS_BEING_BORROWED);
        bookCopyRepository.deleteById(bookCopyId);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<BookCopyResponse> createCopies(CreationBookCopiesRequest request) {
        Book book = bookService.findBookById(request.getBookId());

        List<BookCopy> newCopies = new ArrayList<>();
        for (int i = 0; i < request.getNumberOfCopies(); i++) {
            BookCopy newCopy = BookCopy.builder()
                    .book(book)
                    .status(BookCopyStatus.AVAILABLE)
                    .build();
            newCopies.add(newCopy);
        }

        List<BookCopy> savedCopies = bookCopyRepository.saveAll(newCopies);
        return savedCopies.stream()
                .map(bookCopyMapper::toBookCopyResponse)
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public BookCopyResponse updateBookCopy(String bookCopyId, BookCopyRequest request) {
        BookCopy bookCopy = findById(bookCopyId);

        bookCopyMapper.updateBookCopy(bookCopy, request);
        bookCopy = bookCopyRepository.save(bookCopy);
        return bookCopyMapper.toBookCopyResponse(bookCopy);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public BookCopyResponse getDetails(String bookCopyId) {
            BookCopy bookCopy = findById(bookCopyId);
            return bookCopyMapper.toBookCopyResponse(bookCopy);
    }

    public BookCopy findById(String bookCopyId) {
        return  bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_FOUND));
    }
}
