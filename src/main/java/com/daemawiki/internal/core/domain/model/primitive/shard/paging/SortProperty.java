package com.daemawiki.internal.core.domain.model.primitive.shard.paging;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record SortProperty(
        @JsonValue
        String sortProperty
) implements DomainPrimitive.StringDP {

    public SortProperty {
        assertArgumentNotEmpty(sortProperty, "정렬 속성이 입력되지 않았습니다.");
    }

    public static SortProperty create(final String sortProperty) {
        return new SortProperty(sortProperty);
    }

    @Override
    public String value() {
        return sortProperty;
    }

}
