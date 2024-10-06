package com.example.library.service;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.entity.Publisher;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import com.example.library.repository.PublisherRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;
    PublisherRepository publisherRepository;
    AuthorRepository authorRepository;
    CategoryRepository categoryRepository;

    @Transactional
    public BookResponse create(BookRequest request) {
        Book book = bookMapper.toBook(request);

        // Set publisher
        Publisher publisher = publisherRepository.findById(request.getPublisher())
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
        book.setPublisher(publisher);

        // Set authors
        Set<Author> authors = new HashSet<>();
        for (String authorId : request.getAuthors()) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
            authors.add(author);
        }
        book.setAuthors(authors);

        // Set categories
        Set<Category> categories = new HashSet<>();
        for (String categoryId : request.getCategories()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            categories.add(category);
        }
        book.setCategories(categories);

        try {
            book = bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BOOK_ALREADY_EXISTS);
        }

        return bookMapper.toBookResponse(book);
    }

    public BookResponse update(BookRequest request, String bookID) {
        // Retrieve the existing book from the repository
        Book existingBook = bookRepository.findById(bookID)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        // Update fields from the request
        existingBook.setTitle(request.getTitle());
        existingBook.setPublicationYear(request.getPublicationYear());
        existingBook.setPrice(request.getPrice());

        // Update publisher
        Publisher publisher = publisherRepository.findById(request.getPublisher())
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
        existingBook.setPublisher(publisher);

        // Update authors
        Set<Author> authors = new HashSet<>();
        for (String authorId : request.getAuthors()) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
            authors.add(author);
        }
        existingBook.setAuthors(authors);

        // Update categories
        Set<Category> categories = new HashSet<>();
        for (String categoryId : request.getCategories()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            categories.add(category);
        }
        existingBook.setCategories(categories);

        // Save the updated book back to the repository
        try {
            existingBook = bookRepository.save(existingBook);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BOOK_ALREADY_EXISTS);
        }

        // Return the updated BookResponse
        return bookMapper.toBookResponse(existingBook);
    }

    public List<BookResponse> getAll() {
        var books = bookRepository.findAll();
        return books.stream().map(bookMapper::toBookResponse).toList();
    }

    public BookResponse getDetails(String bookID) {
        Book book = bookRepository.findById(bookID)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        return bookMapper.toBookResponse(book);
    }



    public void delete(String bookID) {
        bookRepository.deleteById(bookID);
    }

}
