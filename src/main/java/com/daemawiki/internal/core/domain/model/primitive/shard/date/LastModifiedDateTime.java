package com.daemawiki.internal.core.domain.model.primitive.shard.date;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDateTime;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record LastModifiedDateTime(
        @JsonValue
        String lastModifiedDateTime
) implements DomainPrimitive.StringDP {

    public LastModifiedDateTime {
        assertArgumentNotEmpty(lastModifiedDateTime, "마지막 수정 날짜가 입력되지 않았습니다");
    }

    public static LastModifiedDateTime create(String lastModifiedDateTime) {
        return new LastModifiedDateTime(lastModifiedDateTime);
    }

    public static LastModifiedDateTime create(LocalDateTime lastModifiedDateTime) {
        return new LastModifiedDateTime(lastModifiedDateTime.toString());
    }

    @Override
    public String value() {
        return lastModifiedDateTime;
    }

}
