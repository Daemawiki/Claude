package com.daemawiki.internal.core.domain.model.primitive.shard.paging;

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
