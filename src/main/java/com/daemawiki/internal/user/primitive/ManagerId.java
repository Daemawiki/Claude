package com.daemawiki.internal.user.primitive;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonValue;

public record ManagerId(
        @JsonValue
        String managerId
) implements DomainPrimitive<String> {

    public static ManagerId create(final String managerId) {
        return new ManagerId(managerId);
    }

    @Override
    public String value() {
        return managerId;
    }

}
