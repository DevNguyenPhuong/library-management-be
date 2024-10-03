package com.example.library.mapper;

import com.example.library.dto.request.CategoryRequest;
import com.example.library.dto.response.CategoryResponse;
import com.example.library.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromRequest(CategoryRequest request, @MappingTarget Category category);
}