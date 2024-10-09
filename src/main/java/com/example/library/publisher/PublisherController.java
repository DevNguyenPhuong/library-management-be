package com.example.library.publisher;

import com.example.library.dto.publisher.PublisherRequest;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.publisher.PublisherResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublisherController {
    PublisherService publisherService;

    @PostMapping
    ApiResponse<PublisherResponse> create(@RequestBody PublisherRequest request) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.create(request))
                .build();
    }

    @GetMapping("/{publisherID}")
    ApiResponse<PublisherResponse> getDetails(@PathVariable String publisherID) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.getDetails(publisherID))
                .build();
    }

    @PutMapping("/{publisherID}")
    ApiResponse<PublisherResponse> update(@PathVariable String publisherID, @RequestBody PublisherRequest request) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.update(publisherID, request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PublisherResponse>> getAll() {
        return ApiResponse.<List<PublisherResponse>>builder()
                .result(publisherService.getAll())
                .build();
    }

    @DeleteMapping("/{publisherID}")
    ApiResponse<Void> delete(@PathVariable String publisherID) {
        publisherService.delete(publisherID);
        return ApiResponse.<Void>builder().build();
    }
}