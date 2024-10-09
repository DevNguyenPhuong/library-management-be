package com.example.library.fine;

import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FineMapper {
    Fine toFine(FineRequest fineRequest);

    FineResponse toFineResponse(Fine fine);
}
