package com.daemawiki.internal.core.domain.model.primitive.user.student;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentLength;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record ClassNumber(
        @JsonValue
        Integer classNumber
) implements DomainPrimitive.IntegerDP {

    public ClassNumber {
        assertArgumentNotNull(classNumber, "반 번호가 입력되지 않았습니다.");
        assertArgumentLength(classNumber, 1, 4, "반 번호의 최소 입력값은 1, 최대 입력값은 4입니다.");
    }

    public static ClassNumber create(final Integer classNumber) {
        return new ClassNumber(classNumber);
    }

    @Override
    public Integer value() {
        return classNumber;
    }

}
