package com.daemawiki.external.exception;

import com.daemawiki.external.exception.custom.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ExceptionResponse(
        int status,
        String message,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
        LocalDateTime timestamp,
        String errorId
) {
    public ExceptionResponse {
        final var httpStatus = HttpStatus.valueOf(status);

        if (httpStatus.is5xxServerError()) {
            message = httpStatus.name();
        }

        errorId = UUID.randomUUID().toString().substring(0, 7);
    }

    public static ExceptionResponse ofCustomException(CustomException e) {
        return new ExceptionResponse(
                e.getStatus().value(),
                e.getMessage(),
                LocalDateTime.now(),
                null
        );
    }

    public static ExceptionResponse ofSecurityError(HttpStatus status, String message) {
        return new ExceptionResponse(
                status.value(),
                message,
                LocalDateTime.now(),
                null
        );
    }
}
