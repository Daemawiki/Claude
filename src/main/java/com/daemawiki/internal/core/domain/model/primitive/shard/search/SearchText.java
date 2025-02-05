package com.daemawiki.internal.core.domain.model.primitive.shard.search;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record SearchText(
        @JsonValue
        String searchText
) implements DomainPrimitive.StringDP {

    public SearchText {
        assertArgumentNotEmpty(searchText, "검색할 단어가 입력되지 않았습니다."); // TODO: 2/4/25 error message 한 번 더 고민
    }

    public static SearchText create(final String searchText) {
        return new SearchText(searchText);
    }

    @Override
    public String value() {
        return searchText;
    }

}
