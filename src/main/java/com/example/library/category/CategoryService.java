package com.example.library.category;

import com.example.library.book.BookRepository;
import com.example.library.dto.Category.CategoryBookResponse;
import com.example.library.dto.Category.CategoryRequest;
import com.example.library.dto.Category.CategoryResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    BookRepository bookRepository;

    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);

        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
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

    public List<CategoryBookResponse> getCategoryStatistics() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> {
                    long bookCount = bookRepository.countByCategories(category);
                    return new CategoryBookResponse(category.getName(), bookCount);
                })
                .collect(Collectors.toList());
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
