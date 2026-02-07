package com.daemawiki.internal.common.primitive.shard;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDateTime;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record CreatedDateTime(
        @JsonValue
        String createdDateTime
) implements DomainPrimitive<String> {

    public CreatedDateTime {
        assertArgumentNotEmpty(createdDateTime, "생성된 날짜가 입력되지 않았습니다");
    }

    public static CreatedDateTime create(String createdDateTime) {
        return new CreatedDateTime(createdDateTime);
    }

    public static CreatedDateTime create(LocalDateTime createdDateTime) {
        return new CreatedDateTime(createdDateTime.toString());
    }

    @Override
    public String value() {
        return createdDateTime;
    }

}
