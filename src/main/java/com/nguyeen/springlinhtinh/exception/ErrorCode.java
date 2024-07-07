package com.nguyeen.springlinhtinh.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //User & Authentication
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    INVALID_KEY(1004, "Invalid Key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User already existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002, "Username must have at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must have at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED), // 401
    // 401 k được xử lý bởi Global bởi nó xảy ra trên các tầng filter -> xử lý = secuConfig
    UNAUTHORIZED(1007, "You do not have permission ", HttpStatus.FORBIDDEN), // 403
    DOB_INVALID(1008, "Your date of birth at least {min}", HttpStatus.BAD_REQUEST),
    //Product
    PRODUCT_EXISTED(1011, "Product already existed", HttpStatus.BAD_REQUEST),
    //Category
    CATEGORY_EXISTED(1021, "Category already existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1022, "Category not found", HttpStatus.BAD_REQUEST),
    CATEGORY_PARENT_NOT_VALID(1023, "Can't add product to parent categories", HttpStatus.BAD_REQUEST)

    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
