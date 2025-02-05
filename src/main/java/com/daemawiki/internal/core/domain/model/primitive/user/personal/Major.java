package com.daemawiki.internal.core.domain.model.primitive.user.personal;

import com.daemawiki.internal.common.assertion.RegexUtils;
import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertRegularExpression;

public record Major(
        @JsonValue
        String major
) implements DomainPrimitive.StringDP {

    private static final Pattern MAJOR_REGEX = Pattern.compile(RegexUtils.ALLOWED_ONLY_KOR_N_ENG.getRegex());

    public Major {
        assertArgumentNotEmpty(major, "전공이 입력되지 않았습니다.");
        assertRegularExpression(major, MAJOR_REGEX, "전공은 한글 또는 영문으로 입력해야합니다.");
    }

    public static Major create(final String major) {
        return new Major(major);
    }

    @Override
    public String value() {
        return major;
    }

}
