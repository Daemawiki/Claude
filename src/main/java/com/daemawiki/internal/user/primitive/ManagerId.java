package com.daemawiki.internal.user.primitive;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

public record ManagerId(
        @JsonValue
        String managerId
) implements DomainPrimitive.StringDP {

    public static ManagerId create(final String managerId) {
        return new ManagerId(managerId);
    }

    @Override
    public String value() {
        return managerId;
    }

}
