package com.example.library.author;

import com.example.library.book.Book;
import com.example.library.book.BookRepository;
import com.example.library.dto.author.AuthorRequest;
import com.example.library.dto.author.AuthorResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.publisher.Publisher;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;
    BookRepository bookRepository;

    @PreAuthorize("hasRole('LIBRARIAN')")
    public AuthorResponse create(AuthorRequest request) {
        Author author = authorMapper.toAuthor(request);
        author = authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public AuthorResponse getDetails(String authorId) {
        Author author = findAuthorById(authorId);
        return authorMapper.toAuthorResponse(author);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public AuthorResponse update(String authorId, AuthorRequest request) {
        Author author = findAuthorById(authorId);
        authorMapper.updateAuthorFromRequest(request, author);
        author = authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<AuthorResponse> getAll() {
        var authors = authorRepository.findAll();
        return authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .map(authorMapper::toAuthorResponse)
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteAuthor(String authorId) {
        Author author = findAuthorById(authorId);
        List<Book> books = bookRepository.findByAuthorsContaining(author);

        for (Book book : books)
            book.getAuthors().remove(author);


        bookRepository.saveAll(books);
        authorRepository.delete(author);
    }

    public Author findAuthorById(String authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
    }
}
