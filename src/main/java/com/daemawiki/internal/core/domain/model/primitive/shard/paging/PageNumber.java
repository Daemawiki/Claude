package com.daemawiki.internal.core.domain.model.primitive.shard.paging;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record PageNumber(
        @JsonValue
        @JsonProperty("page")
        Integer pageNumber
) implements DomainPrimitive.IntegerDP {

    public PageNumber {
        if (pageNumber == null) {
            pageNumber = 0;
        }
    }

    public static PageNumber create(final Integer pageNumber) {
        return new PageNumber(pageNumber);
    }

    @Override
    public Integer value() {
        return pageNumber;
    }

}
