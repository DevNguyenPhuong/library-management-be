package com.example.library.Libarian;

import com.example.library.dto.Librarian.LibrarianCreationRequest;
import com.example.library.dto.Librarian.LibrarianResponse;
import com.example.library.dto.Librarian.LibrarianUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibrarianMapper {
    Librarian toLibrarian(LibrarianCreationRequest request);

    LibrarianResponse toLibrarianResponse(Librarian librarian);

    void updateLibrarian(@MappingTarget Librarian librarian, LibrarianUpdateRequest request);
}
