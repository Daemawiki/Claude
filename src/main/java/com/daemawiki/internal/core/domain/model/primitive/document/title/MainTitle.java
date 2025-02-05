package com.daemawiki.internal.core.domain.model.primitive.document.title;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record MainTitle(
        @JsonValue
        @JsonProperty("main")
        String mainTitle
) implements DomainPrimitive.StringDP {

    public MainTitle {
        assertArgumentNotEmpty(mainTitle, "문서의 제목이 입력되지 않았습니다");
    }

    public static MainTitle create(final String mainTitle) {
        return new MainTitle(mainTitle);
    }

    @Override
    public String value() {
        return mainTitle;
    }

}
