package com.daemawiki.internal.core.domain.model.primitive.document;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record DocumentCategory(
        @JsonValue
        @JsonProperty("category")
        String documentCategory
) implements DomainPrimitive.StringDP {

    public DocumentCategory {
        assertArgumentNotEmpty(documentCategory, "문서의 카테고리가 입력되지 않았습니다");
    }

    public static DocumentCategory create(final String category) {
        return new DocumentCategory(category);
    }

    @Override
    public String value() {
        return documentCategory;
    }

}
