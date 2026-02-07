package com.daemawiki.internal.user.primitive;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record UserId(
        @JsonValue
        String userId
) implements DomainPrimitive<String> {

    public UserId {
        assertArgumentNotEmpty(userId, "유저 아이디가 입력되지 않았습니다.");
    }

    public static UserId create(final String userId) {
        return new UserId(userId);
    }

    @Override
    public String value() {
        return userId;
    }

}
