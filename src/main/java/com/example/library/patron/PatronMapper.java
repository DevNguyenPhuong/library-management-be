package com.example.library.patron;

import com.example.library.constant.LoanStatus;
import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.loan.LoanRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PatronMapper {
    Patron toPatron(PatronRequest request);

    @Mapping(target = "currentlyBorrowed", ignore = true)
    PatronResponse toPatronResponse(Patron patron);


    void updatePatron(@MappingTarget Patron patron, PatronRequest request);
}