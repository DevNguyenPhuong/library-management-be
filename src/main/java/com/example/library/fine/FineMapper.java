package com.example.library.fine;

import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FineMapper {
    @Mapping(target = "patron", ignore = true)
    @Mapping(target = "loan", ignore = true)
    Fine toFine(FineRequest fineRequest);

    @Mapping(target = "loan", source = "loan")
    FineResponse toFineResponse(Fine fine);

    @Mapping(target = "patron", ignore = true)
    @Mapping(target = "loan", ignore = true)
    void updateFine(@MappingTarget Fine fine, FineRequest fineRequest);
}
