package com.daemawiki.daemawiki.common.error.exception;

import org.springframework.http.HttpStatus;

public final class CustomExceptionFactory {

    public static CustomException httpStatusException(HttpStatus httpStatus) {
        return new CustomException(
                httpStatus,
                httpStatus.getReasonPhrase()
        );
    }

    public static CustomException internalServerError(String message) {
        return new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static CustomException internalServerError(String message, Throwable cause) {
        return new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

    public static CustomException notFound(String message) {
        return new CustomException(HttpStatus.NOT_FOUND, message);
    }

    public static CustomException notFound(String message, Throwable cause) {
        return new CustomException(HttpStatus.NOT_FOUND, message, cause);
    }

    public static CustomException forbidden(String message) {
        return new CustomException(HttpStatus.FORBIDDEN, message);
    }

    public static CustomException forbidden(String message, Throwable cause) {
        return new CustomException(HttpStatus.FORBIDDEN, message, cause);
    }
  
    public static CustomException conflict(String message) {
        return new CustomException(HttpStatus.CONFLICT, message);
    }

    public static CustomException conflict(String message, Throwable cause) {
        return new CustomException(HttpStatus.CONFLICT, message, cause);
    }
}
