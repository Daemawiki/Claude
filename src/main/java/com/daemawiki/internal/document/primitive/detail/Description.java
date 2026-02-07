package com.daemawiki.internal.document.primitive.detail;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

public record Description(
        @JsonValue
        String description
) implements DomainPrimitive<String> {

    public static Description create(final String description) {
        return new Description(description);
    }

    @Override
    public String value() {
        return description;
    }

}
