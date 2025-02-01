package com.daemawiki.internal.core.domain.model.primitive.user.personal;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertRegularExpression;
import static com.daemawiki.internal.common.assertion.RegexUtils.DSM_EMAIL;

public record Email(
        @JsonValue
        String email
) implements DomainPrimitive.StringDP {

    private static final Pattern DSM_EMAIL_REGEX = Pattern.compile(DSM_EMAIL.getRegex());

    public Email {
        assertArgumentNotEmpty(email, "이메일이 입력되지 않았습니다.");
        assertRegularExpression(email, DSM_EMAIL_REGEX, "대덕소프트웨어마이스터고등학교 메일 주소를 입력해주세요.");
    }

    public static Email create(final String email) {
        return new Email(email);
    }

    @Override
    public String value() {
        return email;
    }

}
