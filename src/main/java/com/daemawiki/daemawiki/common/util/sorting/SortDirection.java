package com.daemawiki.daemawiki.common.util.sorting;

import lombok.Getter;

@Getter
public enum SortDirection {
    DESC(-1),
    ASC(1);
    private final int directionInt;

    SortDirection(int directionInt) {
        this.directionInt = directionInt;
    }
}
