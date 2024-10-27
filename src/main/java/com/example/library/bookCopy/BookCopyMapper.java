package com.example.library.bookCopy;

import com.example.library.book.Book;
import com.example.library.dto.bookCopy.BookCopyRequest;
import com.example.library.dto.bookCopy.BookCopyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "title", source = "bookCopy.book.title")
    BookCopyResponse toBookCopyResponse(BookCopy bookCopy);

    void updateBookCopy(@MappingTarget BookCopy bookCopy, BookCopyRequest bookCopyRequest);


}