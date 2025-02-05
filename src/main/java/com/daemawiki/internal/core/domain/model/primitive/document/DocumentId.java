package com.daemawiki.internal.core.domain.model.primitive.document;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.daemawiki.internal.common.assertion.AssertionUtils.assertArgumentNotEmpty;

public record DocumentId(
        @JsonValue
        String documentId
) implements DomainPrimitive.StringDP {

    public static DocumentId create(final String documentId) {
        return new DocumentId(documentId);
    }

    @Override
    public String value() {
        return documentId;
    }

}
