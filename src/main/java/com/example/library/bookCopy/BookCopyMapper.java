package com.example.library.bookCopy;

import com.example.library.dto.bookCopy.BookCopyRequest;
import com.example.library.dto.bookCopy.BookCopyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyResponse toBookCopyResponse(BookCopy bookCopy);

    void updateBookCopy(@MappingTarget BookCopy bookCopy, BookCopyRequest bookCopyRequest);
}