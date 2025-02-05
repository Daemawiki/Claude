package com.daemawiki.internal.core.domain.model.primitive.auth;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record SecuredPassword(
        @JsonValue
        String securedPassword
) implements DomainPrimitive.StringDP {

    public SecuredPassword {
        assertArgumentNotEmpty(securedPassword, "비밀번호가 입력되지 않았습니다.");
    }

    public static SecuredPassword create(final String securedPassword) {
        return new SecuredPassword(securedPassword);
    }

    @Override
    public String value() {
        return securedPassword;
    }

}
