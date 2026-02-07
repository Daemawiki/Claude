package com.daemawiki.internal.user.primitive;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record RegistrationDate(
        @JsonValue
        String registrationDate
) implements DomainPrimitive<String> {

    public RegistrationDate {
        assertArgumentNotEmpty(registrationDate, "유저 가입 날짜가 입력되지 않았습니다.");
    }

    public static RegistrationDate create(final String registrationDate) {
        return new RegistrationDate(registrationDate);
    }

    @Override
    public String value() {
        return registrationDate;
    }

}
