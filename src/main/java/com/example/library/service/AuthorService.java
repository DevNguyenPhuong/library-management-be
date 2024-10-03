package com.example.library.service;

import com.example.library.dto.request.AuthorRequest;
import com.example.library.dto.response.AuthorResponse;
import com.example.library.entity.Author;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.mapper.AuthorMapper;
import com.example.library.repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    public AuthorResponse create(AuthorRequest request) {
        Author author = authorMapper.toAuthor(request);
        author = authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    public AuthorResponse getDetails(String authorID) {
        Author author = authorRepository.findById(authorID)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        return authorMapper.toAuthorResponse(author);
    }

    public AuthorResponse update(String authorID, AuthorRequest request) {
        Author author = authorRepository.findById(authorID)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        authorMapper.updateAuthorFromRequest(request, author);
        author = authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    public List<AuthorResponse> getAll() {
        var authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toAuthorResponse).toList();
    }

    public void delete(String authorID) {
        authorRepository.deleteById(authorID);
    }
}
