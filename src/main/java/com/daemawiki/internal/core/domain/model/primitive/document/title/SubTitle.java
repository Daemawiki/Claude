package com.daemawiki.internal.core.domain.model.primitive.document.title;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record SubTitle(
        @JsonValue
        @JsonProperty("sub")
        String subTitle
) implements DomainPrimitive.StringDP {

    public SubTitle {
        assertArgumentNotNull(subTitle, "문서의 부제목이 입력되지 않았습니다");
    }

    public static SubTitle create(final String subTitle) {
        return new SubTitle(subTitle);
    }

    @Override
    public String value() {
        return subTitle;
    }

}
