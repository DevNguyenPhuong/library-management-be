package com.example.library.category;

import com.example.library.dto.Category.CategoryRequest;
import com.example.library.dto.Category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    @Mapping(target = "id", source = "id")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromRequest(CategoryRequest request, @MappingTarget Category category);
}