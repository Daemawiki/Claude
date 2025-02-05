package com.daemawiki.internal.core.domain.model.primitive.user.student;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record StudentNumber(
        @JsonValue
        Integer studentNumber
) implements DomainPrimitive.IntegerDP {

    public StudentNumber {
        assertArgumentNotNull(studentNumber, "학생 번호가 입력되지 않았습니다.");
    }

    public static StudentNumber create(final Integer studentNumber) {
        return new StudentNumber(studentNumber);
    }

    @Override
    public Integer value() {
        return studentNumber;
    }

}
