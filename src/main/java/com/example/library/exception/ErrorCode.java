package com.example.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1009, "Category is not exist", HttpStatus.NOT_FOUND),
    AUTHOR_NOT_FOUND(1010, "Author is not exist", HttpStatus.NOT_FOUND),
    PUBLISHER_NOT_FOUND(1011, "Publisher is not exist", HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND(1012, "Book is not exist", HttpStatus.NOT_FOUND),
    BOOK_ALREADY_EXISTS(1013, "Book is already exist", HttpStatus.BAD_REQUEST),
    INVALID_PATRON_ID(10014, "Patron ID must be exactly 12 characters", HttpStatus.BAD_REQUEST),
    PATRON_NOT_FOUND(1015, "Patron is not exist", HttpStatus.NOT_FOUND),
    BOOK_COPY_NOT_FOUND(1016, "Book copy is not exist", HttpStatus.NOT_FOUND),
    LOAN_NOT_FOUND(1017, "Loan is not exist", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
