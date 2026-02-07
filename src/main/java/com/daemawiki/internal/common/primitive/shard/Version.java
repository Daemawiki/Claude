package com.daemawiki.internal.common.primitive.shard;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record Version(
        @JsonValue
        Long version
) implements DomainPrimitive<Long> {

    public Version {
        assertArgumentNotNull(version, "버전이 입력되지 않았습니다");
    }

    public static Version create(final Long version) {
        return new Version(version);
    }

    @Override
    public Long value() {
        return version;
    }

}
