package com.daemawiki.internal.core.domain.model.primitive.shard.info;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record ViewCount(
        @JsonValue
        Long viewCount
) implements DomainPrimitive.LongDP {

    public ViewCount {
        assertArgumentNotNull(viewCount, "조회수가 입력되지 않았습니다");
    }

    public static ViewCount create(final Long viewCount) {
        return new ViewCount(viewCount);
    }

    @Override
    public Long value() {
        return viewCount;
    }

}
