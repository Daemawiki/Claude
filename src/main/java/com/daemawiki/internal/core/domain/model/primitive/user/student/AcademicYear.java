package com.daemawiki.internal.core.domain.model.primitive.user.student;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record AcademicYear(
        @JsonValue
        Integer academicYear
) implements DomainPrimitive.IntegerDP {

    public AcademicYear {
        assertArgumentNotNull(academicYear, "학년도가 입력되지 않았습니다.");
    }

    public static AcademicYear create(final Integer academicYear) {
        return new AcademicYear(academicYear);
    }

    @Override
    public Integer value() {
        return academicYear;
    }

}
