package com.example.library.patron;

import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.patron.PatronResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface PatronMapper {
    Patron toPatron(PatronRequest request);

    PatronResponse toPatronResponse(Patron patron);


    void updatePatron(@MappingTarget Patron patron, PatronRequest request);
}
