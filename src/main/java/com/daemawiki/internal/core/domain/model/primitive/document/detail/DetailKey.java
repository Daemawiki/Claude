package com.daemawiki.internal.core.domain.model.primitive.document.detail;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record DetailKey(
        @JsonValue
        String detailKey
) implements DomainPrimitive.StringDP {

    public DetailKey {
        assertArgumentNotEmpty(detailKey, "키가 입력되지 않았습니다");
    }

    public static DetailKey create(final String detailKey) {
        return new DetailKey(detailKey);
    }

    @Override
    public String value() {
        return detailKey;
    }

}
