package com.daemawiki.internal.mail.primitive;

import com.daemawiki.internal.common.random.AuthCodeGenerator;
import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record AuthCode(
        @JsonValue
        String authCode
) implements DomainPrimitive<String> {

    public AuthCode {
        assertArgumentNotEmpty(authCode, "인증 코드가 입력되지 않았습니다.");
    }

    public static AuthCode create(final String authCode) {
        return new AuthCode(authCode);
    }

    public static AuthCode create() {
        return new AuthCode(AuthCodeGenerator.generate(6));
    }

    @Override
    public String value() {
        return authCode;
    }

}
