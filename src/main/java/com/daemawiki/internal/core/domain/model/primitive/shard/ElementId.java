package com.daemawiki.internal.core.domain.model.primitive.shard;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record ElementId(
        @JsonValue
        Integer elementId
) implements DomainPrimitive.IntegerDP {

    public ElementId {
        assertArgumentNotNull(elementId, "요소의 아이디가 입력되지 않았습니다.");
    }

    public static ElementId create(final Integer elementId) {
        return new ElementId(elementId);
    }

    @Override
    public Integer value() {
        return elementId;
    }

}
