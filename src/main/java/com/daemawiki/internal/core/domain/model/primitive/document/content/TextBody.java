package com.daemawiki.internal.core.domain.model.primitive.document.content;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotNull;

public record TextBody(
        @JsonValue
        String textBody
) implements DomainPrimitive.StringDP {

    public TextBody {
        assertArgumentNotNull(textBody, "문서의 내용이 입력되지 않았습니다");
    }

    public static TextBody create(final String textBody) {
        return new TextBody(textBody);
    }

    public static TextBody createEmpty() {
        return create("");
    }

    @Override
    public String value() {
        return textBody;
    }

}
