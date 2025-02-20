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
    INVALID_USERNAME(400101, "Username must be at least 5 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400102, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_DOB(400103, "Your age must be at least 5", HttpStatus.BAD_REQUEST),
    INVALID_PATRON_ID(400104, "Patron ID must be exactly 12 characters", HttpStatus.BAD_REQUEST),
    INVALID_PUBLICATION_YEAR(400105, "Publication year only fromm 1000 to now", HttpStatus.BAD_REQUEST),
    INVALID_COPIES(400106, "At least one copies should be created", HttpStatus.BAD_REQUEST),
    INVALID_BORROWED_BOOK(400107, "Borrowed book should between 0 and 5", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(400200, "value cannot be empty", HttpStatus.BAD_REQUEST),
    POSITIVE_REQUIRED(400201, "value must be positive", HttpStatus.BAD_REQUEST),
    IMAGE_SIZE_TOO_LARGE(400202, "Image size too large", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_TYPE(400203, "Invalid image type", HttpStatus.BAD_REQUEST),
    CANNOT_STORE_EMPTY_IMG(400204, "Cannot store empty image", HttpStatus.BAD_REQUEST),
    FAIL_STORE_IMG(400205, "Fail to store image", HttpStatus.BAD_REQUEST),
    FAIL_DELETE_IMG(400206, "Fail to delete image", HttpStatus.BAD_REQUEST),
    FAIL_CREATE_DIR(400207, "Fail to create directory", HttpStatus.BAD_REQUEST),
    FAIL_READ_IMG(400208, "Fail to read image", HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD(400209, "Invalid current password", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH(400210, "Password mismatch", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_SAME_AS_OLD(400211, "Password same as old", HttpStatus.BAD_REQUEST),


    // 409xxx - Conflict errors
    RESOURCE_ALREADY_EXISTS(409000, "Resource already exists", HttpStatus.CONFLICT),
    USER_ALREADY_EXISTS(409001, "User already exists", HttpStatus.CONFLICT),
    BOOK_ALREADY_EXISTS(409002, "Book already exists", HttpStatus.CONFLICT),
    CATEGORY_ALREADY_EXISTS(409003, "Category already exists", HttpStatus.CONFLICT),
    PUBLISHER_ALREADY_EXISTS(409004, "Publisher already exists", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS(409005, "Phone already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS(409006, "Email already exists", HttpStatus.CONFLICT),
    ISBN_ALREADY_EXISTS(409007, "ISBN already exists", HttpStatus.CONFLICT),
    PATRON_ALREADY_EXISTS(409008, "Patron already exists", HttpStatus.CONFLICT),
    LIBRARIAN_ALREADY_EXISTS(409009, "Patron already exists", HttpStatus.CONFLICT),
    PERSONAL_ID_ALREADY_EXISTS(409010, "Personal ID already exists", HttpStatus.CONFLICT),
    SESSION_ALREADY_EXISTS(409011, "Session already exists", HttpStatus.CONFLICT),
    ITEM_ALREADY_EXISTS(409012, "Item already exists", HttpStatus.CONFLICT),


    // 404xxx - Not found errors
    USER_NOT_FOUND(404001, "User not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(404002, "Category not found", HttpStatus.NOT_FOUND),
    AUTHOR_NOT_FOUND(404003, "Author not found", HttpStatus.NOT_FOUND),
    PUBLISHER_NOT_FOUND(404004, "Publisher not found", HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND(404005, "Book not found", HttpStatus.NOT_FOUND),
    PATRON_NOT_FOUND(404006, "Patron not found", HttpStatus.NOT_FOUND),
    BOOK_COPY_NOT_FOUND(404007, "Book copy not found", HttpStatus.NOT_FOUND),
    LOAN_NOT_FOUND(404008, "Loan not found", HttpStatus.NOT_FOUND),
    FINE_NOT_FOUND(404009, "Fine not found", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND(404010, "Image not found", HttpStatus.NOT_FOUND),
    LIBRARIAN_NOT_FOUND(404011, "Librarian not found", HttpStatus.NOT_FOUND),
    SESSION_NOT_FOUND(404012, "Session not found", HttpStatus.NOT_FOUND),
    ITEMS_NOT_FOUND(404013, "Items not found", HttpStatus.NOT_FOUND),

    // 422xxx - Precondition/State errors
    BOOK_COPY_NOT_AVAILABLE(422001, "Book copy is not available for borrowing", HttpStatus.UNPROCESSABLE_ENTITY),
    BOOK_COPY_ALREADY_BORROWED(422002, "Book copy is already borrowed", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_HAS_OVERDUE_BOOKS(422003, "Patron has overdue books", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_REACHED_LOAN_LIMIT(422004, "Patron has reached maximum loan limit", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_HAS_ALREADY_BORROWED_THIS_BOOK(422005, "Patron has already borrowed this book", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_NOT_ACTIVE(422006, "Patron account is not active", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_MEMBERSHIP_EXPIRED(422007, "Patron membership has expired", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_ALREADY_HAS_BOOK(422008, "Patron already has a copy of this book", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_HAS_OUTSTANDING_FINES(422009, "Patron has unpaid fines", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_HAS_OVERDUE_LOANS(422010, "Patron has overdue loans", HttpStatus.UNPROCESSABLE_ENTITY),
    THIS_LOAN_HAVE_FINE(422011, "Loan already has fine", HttpStatus.UNPROCESSABLE_ENTITY),
    BOOK_HAS_BORROWED_COPIES(422012, "Book have/has borrowed copies", HttpStatus.UNPROCESSABLE_ENTITY),
    BOOK_COPY_IS_BEING_BORROWED(422012, "This copy is being borrowed", HttpStatus.UNPROCESSABLE_ENTITY),
    PATRON_DEPOSIT_IS_TOO_LOW(422013, "Patron's deposit is too low", HttpStatus.UNPROCESSABLE_ENTITY),
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
