package com.daemawiki.internal.core.domain.model.primitive.user.personal;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.Year;
import java.time.ZoneId;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentLength;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record Generation(
        @JsonValue
        Integer generation
) implements DomainPrimitive.IntegerDP {

    public Generation {
        assertArgumentNotNull(generation, "기수가 입력되지 않았습니다.");

        final var MaxGeneration = Year.now(ZoneId.of("Asia/Seoul")).getValue() - 2014;
        assertArgumentLength(
                generation,
                1,
                MaxGeneration,
                "기수의 최소 입력값은 1, 최대 입력값은 " + MaxGeneration + "입니다."
        );
    }

    public static Generation create(final Integer generation) {
        return new Generation(generation);
    }

    @Override
    public Integer value() {
        return generation;
    }

}
