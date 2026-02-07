package com.daemawiki.internal.common.primitive.shard;

public enum SortDirection {

    DESC(-1),

    ASC(1);

    private final int value;

    SortDirection(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
