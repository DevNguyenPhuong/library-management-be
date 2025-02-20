package com.example.library.book;

import com.example.library.author.AuthorService;
import com.example.library.bookCopy.BookCopyRepository;
import com.example.library.category.CategoryService;
import com.example.library.constant.BookCopyStatus;
import com.example.library.dto.book.BookRequest;
import com.example.library.dto.book.BookResponse;
import com.example.library.image.ImageService;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.publisher.PublisherService;
import com.example.library.validator.ImageValidator;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;
    PublisherService publisherService;
    CategoryService categoryService;
    AuthorService authorService;
    BookCopyRepository bookCopyRepository;
    ImageService imageService;
    ImageValidator imageValidator;

    static String BOOKS_FOLDER = "books";

    @PreAuthorize("hasRole('LIBRARIAN')")
    public BookResponse createBook(BookRequest request, MultipartFile image) {
        if(bookRepository.existsByIsbn(request.getIsbn())){
            throw new AppException(ErrorCode.ISBN_ALREADY_EXISTS);
        }
        Book book = bookMapper.toBook(request);
        enrichBookData(book, request);
        handleImageUpload(book, image, null, false);
        return saveBook(book);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public BookResponse updateBook(BookRequest request, String bookId, MultipartFile image) {
        Book existingBook = findBookById(bookId);
        bookMapper.updateBook(existingBook, request);
        enrichBookData(existingBook, request);
        handleImageUpload(existingBook, image, existingBook.getImageUrl(), request.getIsDeleteImg());
        return saveBook(existingBook);
    }


    public Page<BookResponse> getBooks(String query, Pageable pageable) {
        Page<Book> books;
        if (query == null || query.isEmpty()) {
            books = bookRepository.findAll(pageable);
        } else {
            books = bookRepository.findByTitleContainingIgnoreCaseOrAuthors_NameContainingIgnoreCase(query, query, pageable);
        }
        return books.map(bookMapper::toBookResponse);
    }

    public BookResponse getBook(String bookId) {
        Book book = findBookById(bookId);
        return bookMapper.toBookResponse(book);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteBook(String bookId) {
        Book book = findBookById(bookId);
        long borrowedCopies = bookCopyRepository.countByBookIdAndStatus(bookId, BookCopyStatus.BORROWED);
        if (borrowedCopies > 0)
            throw new AppException(ErrorCode.BOOK_HAS_BORROWED_COPIES);
        if(!Objects.equals(book.getImageUrl(), ""))
            imageService.deleteImage(book.getImageUrl(), "books");

        bookCopyRepository.deleteAllByBookId(bookId);
        bookRepository.delete(book);
    }

    private void enrichBookData(Book book, BookRequest request) {
        setPublisher(book, request.getPublisher());
        setAuthors(book, request.getAuthors());
        setCategories(book, request.getCategories());
    }

    private void setPublisher(Book book, String publisherId) {
        book.setPublisher(publisherService.findPublisherById(publisherId));
    }

    private void setAuthors(Book book, Set<String> authorIds) {
        book.setAuthors(authorIds.stream()
                .map(authorService::findAuthorById)
                .collect(Collectors.toSet()));
    }

    private void setCategories(Book book, Set<String> categoryIds) {
        book.setCategories(categoryIds.stream()
                .map(categoryService::findCategoryById)
                .collect(Collectors.toSet()));
    }

    private void handleImageUpload(Book book, MultipartFile image, String existingImageUrl, Boolean isDeleteImg) {
        if (image != null && !image.isEmpty()) {
            if (existingImageUrl != null) {
                imageService.deleteImage(existingImageUrl, BOOKS_FOLDER);
            }
            imageValidator.validate(image);
            String imageName = imageService.storeImage(image, BOOKS_FOLDER);
            book.setImageUrl(imageName);
        }
        else {
            if(isDeleteImg) {
                imageService.deleteImage(book.getImageUrl(), BOOKS_FOLDER);
            }
            book.setImageUrl("");
        }
    }

    private BookResponse saveBook(Book book) {
        try {
            book = bookRepository.save(book);
            return bookMapper.toBookResponse(book);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BOOK_ALREADY_EXISTS);
        }
    }

    public Book findBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
    }
}
