package com.example.library.mapper;

import com.example.library.dto.request.CategoryRequest;
import com.example.library.dto.request.PublisherRequest;
import com.example.library.dto.response.CategoryResponse;
import com.example.library.dto.response.PublisherResponse;
import com.example.library.entity.Category;
import com.example.library.entity.Publisher;
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
