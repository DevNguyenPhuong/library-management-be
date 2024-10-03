package com.example.library.service;

import com.example.library.dto.request.CategoryRequest;
import com.example.library.dto.response.CategoryResponse;
import com.example.library.entity.Category;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.mapper.CategoryMapper;
import com.example.library.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        var category = categoryRepository.findAll();
        return category.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse getDetails(String categoryID) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() ->  new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse update(String categoryID, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryMapper.updateCategoryFromRequest(request, category);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }


    public void delete(String categoryID) {
        categoryRepository.deleteById(categoryID);
    }
}
