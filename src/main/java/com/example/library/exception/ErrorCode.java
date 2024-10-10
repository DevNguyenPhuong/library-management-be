package com.example.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // 4xx Client Errors
    // 40x - General client errors
    BAD_REQUEST(400000, "Bad request", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(401000, "Unauthorized", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(401000, "Unauthorized", HttpStatus.FORBIDDEN),
    FORBIDDEN(403000, "Forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(404000, "Not found", HttpStatus.NOT_FOUND),

    // 400xxx - Validation errors
    INVALID_INPUT(400100, "Invalid input", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(400101, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400102, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_DOB(400103, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_PATRON_ID(400104, "Patron ID must be exactly 12 characters", HttpStatus.BAD_REQUEST),
    INVALID_PUBLICATION_YEAR(400105, "Publication year only fromm 1000 to now", HttpStatus.BAD_REQUEST),
    INVALID_COPIES(400106, "At least one copies should be created", HttpStatus.BAD_REQUEST),
    INVALID_BORROWED_BOOK(400107, "Borrowed book should between 1 and 5", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(400200, "{field} cannot be empty", HttpStatus.BAD_REQUEST),
    POSITIVE_REQUIRED(400201, "{field} must be positive", HttpStatus.BAD_REQUEST),


    // 409xxx - Conflict errors
    RESOURCE_ALREADY_EXISTS(409000, "Resource already exists", HttpStatus.CONFLICT),
    USER_ALREADY_EXISTS(409001, "User already exists", HttpStatus.CONFLICT),
    BOOK_ALREADY_EXISTS(409002, "Book already exists", HttpStatus.CONFLICT),
    CATEGORY_ALREADY_EXISTS(409002, "Book already exists", HttpStatus.CONFLICT),
    PUBLISHER_ALREADY_EXISTS(409002, "Book already exists", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS(409002, "Book already exists", HttpStatus.CONFLICT),

    // 404xxx - Not found errors
    USER_NOT_FOUND(404001, "User not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(404002, "Category not found", HttpStatus.NOT_FOUND),
    AUTHOR_NOT_FOUND(404003, "Author not found", HttpStatus.NOT_FOUND),
    PUBLISHER_NOT_FOUND(404004, "Publisher not found", HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND(404005, "Book not found", HttpStatus.NOT_FOUND),
    PATRON_NOT_FOUND(404006, "Patron not found", HttpStatus.NOT_FOUND),
    BOOK_COPY_NOT_FOUND(404007, "Book copy not found", HttpStatus.NOT_FOUND),
    LOAN_NOT_FOUND(404008, "Loan not found", HttpStatus.NOT_FOUND),

    // 5xx Server Errors
    INTERNAL_SERVER_ERROR(500000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    // 50xxxx - Specific server errors can be added here

    // Catch-all for uncategorized errors
    UNCATEGORIZED_ERROR(599999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
