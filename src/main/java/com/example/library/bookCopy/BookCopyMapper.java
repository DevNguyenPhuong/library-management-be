package com.example.library.bookCopy;

import com.example.library.dto.bookCopy.BookCopyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyResponse toBookCopyResponse(BookCopy bookCopy);
}