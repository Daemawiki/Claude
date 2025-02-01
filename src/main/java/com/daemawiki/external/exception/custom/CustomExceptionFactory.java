package com.daemawiki.external.exception.custom;

import org.springframework.http.HttpStatus;

public final class CustomExceptionFactory {

    public static CustomException httpStatusException(final HttpStatus httpStatus) {
        return new CustomException(httpStatus, httpStatus.getReasonPhrase());
    }

    public static CustomException internalServerError(final String message) {
        return new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static CustomException internalServerError(
            final String message,
            final Throwable cause
    ) {
        return new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

    public static CustomException badRequest(final String message) {
        return new CustomException(HttpStatus.BAD_REQUEST, message);
    }

    public static CustomException unauthorized(final String message) {
        return new CustomException(HttpStatus.UNAUTHORIZED, message);
    }

    public static CustomException notFound(final String message) {
        return new CustomException(HttpStatus.NOT_FOUND, message);
    }

    public static CustomException notFound(
            final String message,
            final Throwable cause
    ) {
        return new CustomException(HttpStatus.NOT_FOUND, message, cause);
    }

    public static CustomException forbidden(final String message) {
        return new CustomException(HttpStatus.FORBIDDEN, message);
    }

    public static CustomException forbidden(
            final String message,
            final Throwable cause
    ) {
        return new CustomException(HttpStatus.FORBIDDEN, message, cause);
    }

    public static CustomException conflict(final String message) {
        return new CustomException(HttpStatus.CONFLICT, message);
    }

    public static CustomException conflict(
            final String message,
            final Throwable cause
    ) {
        return new CustomException(HttpStatus.CONFLICT, message, cause);
    }
}
