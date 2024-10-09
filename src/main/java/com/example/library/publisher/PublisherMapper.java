package com.example.library.publisher;

import com.example.library.dto.publisher.PublisherRequest;
import com.example.library.dto.publisher.PublisherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toPublisher(PublisherRequest request);

    @Mapping(target = "id", source = "id")
    PublisherResponse toPublisherResponse(Publisher publisher);

    @Mapping(target = "id", ignore = true)
    void updatePublisherFromRequest(PublisherRequest request, @MappingTarget Publisher publisher);
}
