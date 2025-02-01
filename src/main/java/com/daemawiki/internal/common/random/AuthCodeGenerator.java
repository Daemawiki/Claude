package com.daemawiki.internal.common.random;

import java.security.SecureRandom;
import java.util.Base64;

public class AuthCodeGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generate(final int length) {
        final var randomBytes = new byte[length];
        SECURE_RANDOM.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes)
                .substring(0, length);
    }

}
