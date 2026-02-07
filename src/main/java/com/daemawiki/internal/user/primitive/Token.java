package com.daemawiki.internal.user.primitive;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record Token(
        @JsonValue
        String token
) implements DomainPrimitive<String> {

    public Token {
        assertArgumentNotEmpty(token, "토큰이 입력되지 않았습니다.");
    }

    public static Token create(final String token) {
        return new Token(token);
    }

    @Override
    public String value() {
        return token;
    }

}
