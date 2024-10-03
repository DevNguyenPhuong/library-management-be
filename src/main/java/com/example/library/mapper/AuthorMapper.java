package com.example.library.mapper;

import com.example.library.dto.request.AuthorRequest;
import com.example.library.dto.response.AuthorResponse;
import com.example.library.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorRequest request);
    AuthorResponse toAuthorResponse(Author author);

    @Mapping(target = "id", ignore = true)
    void updateAuthorFromRequest(AuthorRequest request, @MappingTarget Author author);
}

