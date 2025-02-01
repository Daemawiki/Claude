package com.daemawiki.internal.core.domain.model.primitive.auth;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertRegularExpression;
import static com.daemawiki.internal.common.assertion.RegexUtils.PASSWORD;

public record Password(
        @JsonValue
        String password
) implements DomainPrimitive.StringDP {

    private static final Pattern PASSWORD_REGEX = Pattern.compile(PASSWORD.getRegex(8, 20));

    public Password {
        assertArgumentNotEmpty(password, "비밀번호가 입력되지 않았습니다.");
        assertRegularExpression(password, PASSWORD_REGEX, "비밀번호는 8~20자의 영문자, 숫자, 특수문자를 포함해야 합니다.");
    }

    public static Password create(final String password) {
        return new Password(password);
    }

    @Override
    public String value() {
        return password;
    }

}
