package com.example.library.author;

import com.example.library.dto.author.AuthorRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.author.AuthorResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorController {
    AuthorService authorService;

    @PostMapping
    ApiResponse<AuthorResponse> create(@RequestBody AuthorRequest request) {
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<AuthorResponse>> getAll() {
        return ApiResponse.<List<AuthorResponse>>builder()
                .result(authorService.getAll())
                .build();
    }

    @GetMapping("/{authorID}")
    ApiResponse<AuthorResponse> getDetails(@PathVariable String authorID) {
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.getDetails(authorID))
                .build();
    }

    @PutMapping("/{authorID}")
    ApiResponse<AuthorResponse> update(@PathVariable String authorID, @RequestBody AuthorRequest request) {
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.update(authorID, request))
                .build();
    }


    @DeleteMapping("/{authorID}")
    ApiResponse<Void> delete(@PathVariable String authorID) {
        authorService.delete(authorID);
        return ApiResponse.<Void>builder().build();
    }
}