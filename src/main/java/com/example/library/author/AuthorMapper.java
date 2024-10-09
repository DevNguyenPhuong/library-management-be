package com.example.library.author;

import com.example.library.dto.author.AuthorRequest;
import com.example.library.dto.author.AuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorRequest request);

    @Mapping(target = "id", source = "id")
    AuthorResponse toAuthorResponse(Author author);

    @Mapping(target = "id", ignore = true)
    void updateAuthorFromRequest(AuthorRequest request, @MappingTarget Author author);
}

