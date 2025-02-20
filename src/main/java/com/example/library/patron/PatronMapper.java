package com.example.library.patron;

import com.example.library.dto.patron.PatronCreationRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.dto.patron.PatronUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PatronMapper {

    Patron toPatron(PatronCreationRequest request);

    @Mapping(target = "currentlyBorrowed", ignore = true)
    PatronResponse toPatronResponse(Patron patron);

    void updatePatron(@MappingTarget Patron patron, PatronUpdateRequest request);
}