package com.example.library.category;

import com.example.library.author.Author;
import com.example.library.book.Book;
import com.example.library.book.BookRepository;
import com.example.library.dto.Category.CategoryBookResponse;
import com.example.library.dto.Category.CategoryRequest;
import com.example.library.dto.Category.CategoryResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    BookRepository bookRepository;

    @PreAuthorize("hasRole('LIBRARIAN')")
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);

        if(categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        return categoryMapper.toCategoryResponse(category);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<CategoryResponse> getAll() {
        var categories = categoryRepository.findAll();
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public CategoryResponse getDetails(String categoryId) {
        Category category = findCategoryById(categoryId);
        return categoryMapper.toCategoryResponse(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CategoryBookResponse> getCategoryStatistics() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> {
                    long bookCount = bookRepository.countByCategories(category);
                    return new CategoryBookResponse(category.getName(), bookCount);
                })
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public CategoryResponse update(String categoryId, CategoryRequest request) {
        Category category =  findCategoryById(categoryId);

        // Check if the new name already exists (and is not the current category's name)
        if (categoryRepository.existsByName(request.getName()) &&
                !category.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        categoryMapper.updateCategoryFromRequest(request, category);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteCategory(String categoryId) {
        Category category = findCategoryById(categoryId);
        List<Book> books = bookRepository.findByCategoriesContaining(category);

        for (Book book : books)
            book.getCategories().remove(category);

        bookRepository.saveAll(books);
        categoryRepository.delete(category);
    }

    public Category findCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
