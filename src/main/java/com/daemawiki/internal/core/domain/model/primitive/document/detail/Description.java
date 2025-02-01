package com.daemawiki.internal.core.domain.model.primitive.document.detail;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

public record Description(
        @JsonValue
        String description
) implements DomainPrimitive.StringDP {

    public static Description create(final String description) {
        return new Description(description);
    }

    @Override
    public String value() {
        return description;
    }

}
