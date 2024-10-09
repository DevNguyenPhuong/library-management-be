package com.example.library.category;

import com.example.library.dto.Category.CategoryRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.Category.CategoryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(request))
                .build();
    }

    @GetMapping("/{categoryID}")
    ApiResponse<CategoryResponse> getDetails(@PathVariable String categoryID) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getDetails(categoryID))
                .build();
    }

    @PutMapping("/{categoryID}")
    ApiResponse<CategoryResponse> update(@PathVariable String categoryID, @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(categoryID, request))
                .build();
    }


    @GetMapping
    ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    @DeleteMapping("/{categoryID}")
    ApiResponse<Void> delete(@PathVariable String categoryID) {
        categoryService.delete(categoryID);
        return ApiResponse.<Void>builder().build();
    }
}
