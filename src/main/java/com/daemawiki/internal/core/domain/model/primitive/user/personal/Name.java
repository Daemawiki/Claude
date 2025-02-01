package com.daemawiki.internal.core.domain.model.primitive.user.personal;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;
import static com.daemawiki.internal.common.assertion.AssertionUtils.assertRegularExpression;
import static com.daemawiki.internal.common.assertion.RegexUtils.ALLOWED_ONLY_KOR;

public record Name(
        @JsonValue
        String name
) implements DomainPrimitive.StringDP {

    private static final Pattern NAME_REGEX = Pattern.compile(ALLOWED_ONLY_KOR.getRegex(2, 8));
    private static final Name EMPTY_INSTANCE = create("");

    public Name {
        assertArgumentNotEmpty(name, "이름이 입력되지 않았습니다.");
        assertRegularExpression(name, NAME_REGEX, "이름은 한글만을 사용해 입력할 수 있고, 최소 입력값은 2, 최대 입력값은 8입니다.");
    }

    public static Name create(final String name) {
        return new Name(name);
    }

    public static Name emptyInstance() {
        return EMPTY_INSTANCE;
    }

    @Override
    public String value() {
        return name;
    }

}
