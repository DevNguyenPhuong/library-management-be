package com.example.library.publisher;

import com.example.library.dto.publisher.PublisherRequest;
import com.example.library.dto.publisher.PublisherResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import jakarta.transaction.Transactional;
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
public class PublisherService {
    PublisherRepository publisherRepository;
    PublisherMapper publisherMapper;

    public PublisherResponse create(PublisherRequest request) {
        Publisher publisher = publisherMapper.toPublisher(request);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toPublisherResponse(publisher);
    }

    public List<PublisherResponse> getAll() {
        var publishers = publisherRepository.findAll();
        return publishers.stream().map(publisherMapper::toPublisherResponse).toList();
    }

    public PublisherResponse getDetails(String publisherID) {
        Publisher publisher = publisherRepository.findById(publisherID)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
        return publisherMapper.toPublisherResponse(publisher);
    }

    @Transactional
    public PublisherResponse update(String publisherID, PublisherRequest request) {
        Publisher publisher = publisherRepository.findById(publisherID)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));

        publisherMapper.updatePublisherFromRequest(request, publisher);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toPublisherResponse(publisher);
    }

    public void delete(String publisherID) {
        publisherRepository.deleteById(publisherID);
    }
}