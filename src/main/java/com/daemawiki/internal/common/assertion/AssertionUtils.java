package com.daemawiki.internal.common.assertion;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;

import java.util.regex.Pattern;

public final class AssertionUtils {

    public static void assertArgumentNotEmpty(
            final String string,
            final String message
    ) {
        if (string == null || string.isBlank()) {
            throw CustomExceptionFactory.badRequest(message);
        }
    }

    public static void assertArgumentNotNull(
            final Object object,
            final String message
    ) {
        if (object == null) {
            throw CustomExceptionFactory.badRequest(message);
        }
    }

    public static void assertRegularExpression(
            final String string,
            final Pattern regex,
            final String message
    ) {
        if (!regex.matcher(string).matches()) {
            throw CustomExceptionFactory.badRequest(message);
        }
    }

    public static void assertArgumentLength(
            final Object object,
            final int minLength,
            final int maxLength,
            final String message
    ) {
        if (object instanceof String string) {
            if (string.length() <= minLength || string.length() >= maxLength) {
                throw CustomExceptionFactory.badRequest(message);
            }
        }

        else if (object instanceof Integer integer) {
            if (integer <= minLength || integer >= maxLength) {
                throw CustomExceptionFactory.badRequest(message);
            }
        }

        else {
            throw CustomExceptionFactory.badRequest("유효하지 않은 입력값입니다.");
        }
    }

}