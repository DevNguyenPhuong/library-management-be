package com.example.library.mapper;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toBook(BookRequest request);


    BookResponse toBookResponse(Book book);
}