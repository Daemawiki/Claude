package com.daemawiki.internal.common.primitive.shard;

import com.daemawiki.internal.common.primitive.DomainPrimitive;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record SizeNumber(
        @JsonValue
        @JsonProperty("size")
        Integer sizeNumber
) implements DomainPrimitive<Integer> {

    public SizeNumber {
        if (sizeNumber == null) {
            sizeNumber = 10;
        }
    }

    public static SizeNumber create(final Integer sizeNumber) {
        return new SizeNumber(sizeNumber);
    }

    @Override
    public Integer value() {
        return sizeNumber;
    }

}
