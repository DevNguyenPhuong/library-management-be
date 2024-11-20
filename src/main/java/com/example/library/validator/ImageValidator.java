package com.example.library.validator;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageValidator {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/gif"
    );

    public void validate(MultipartFile image) {
        validateFileSize(image);
        validateContentType(image);
    }

    private void validateFileSize(MultipartFile image) {
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new AppException(ErrorCode.IMAGE_SIZE_TOO_LARGE);
        }
    }

    private void validateContentType(MultipartFile image) {
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new AppException(ErrorCode.INVALID_IMAGE_TYPE);
        }

        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_TYPE);
        }
    }
}