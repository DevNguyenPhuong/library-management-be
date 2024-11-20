package com.example.library.publisher;

import com.example.library.book.Book;
import com.example.library.book.BookRepository;
import com.example.library.category.Category;
import com.example.library.dto.publisher.PublisherRequest;
import com.example.library.dto.publisher.PublisherResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublisherService {
    PublisherRepository publisherRepository;
    PublisherMapper publisherMapper;
    BookRepository bookRepository;

    @PreAuthorize("hasRole('LIBRARIAN')")
    public PublisherResponse create(PublisherRequest request) {
        Publisher publisher = publisherMapper.toPublisher(request);

        if( publisherRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PUBLISHER_ALREADY_EXISTS);
        }

        try {
            publisher = publisherRepository.save(publisher);
        }
        catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PUBLISHER_ALREADY_EXISTS);
        }
        return publisherMapper.toPublisherResponse(publisher);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<PublisherResponse> getAll() {
        var publishers = publisherRepository.findAll();
        return publishers.stream()
                .sorted(Comparator.comparing(Publisher::getName))
                .map(publisherMapper::toPublisherResponse)
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public PublisherResponse getDetails(String publisherId) {
        Publisher publisher =  findPublisherById(publisherId);
        return publisherMapper.toPublisherResponse(publisher);
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    public PublisherResponse update(String publisherId, PublisherRequest request) {
        Publisher publisher =   findPublisherById(publisherId);

        if (publisherRepository.existsByName(request.getName()) &&
                !publisher.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.PUBLISHER_ALREADY_EXISTS);
        }

        publisherMapper.updatePublisherFromRequest(request, publisher);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toPublisherResponse(publisher);
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deletePublisher(String publisherId) {
        Publisher publisher =  findPublisherById(publisherId);
        List<Book> books = bookRepository.findByPublisher(publisher);

        for (Book book : books)
            book.setPublisher(null);

        bookRepository.saveAll(books);
        publisherRepository.delete(publisher);
    }

    public Publisher findPublisherById(String publisherId) {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
    }
}