package com.daemawiki.internal.document.primitive;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

public record DocumentId(
        @JsonValue
        String documentId
) implements DomainPrimitive<String> {

    public static DocumentId create(final String documentId) {
        return new DocumentId(documentId);
    }

    @Override
    public String value() {
        return documentId;
    }

}
