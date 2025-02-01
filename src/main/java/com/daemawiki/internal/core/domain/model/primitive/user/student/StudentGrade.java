package com.daemawiki.internal.core.domain.model.primitive.user.student;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentLength;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record StudentGrade(
        @JsonValue
        Integer studentGrade
) implements DomainPrimitive.IntegerDP {

    public StudentGrade {
        assertArgumentNotNull(studentGrade, "학년이 입력되지 않았습니다.");
        assertArgumentLength(studentGrade, 1, 3, "학년의 최소 입력값은 1, 최대 입력값은 3입니다.");
    }

    public static StudentGrade create(final Integer studentGrade) {
        return new StudentGrade(studentGrade);
    }

    @Override
    public Integer value() {
        return studentGrade;
    }

}
