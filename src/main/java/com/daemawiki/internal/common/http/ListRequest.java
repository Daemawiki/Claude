package com.daemawiki.internal.common.http;

import java.util.List;

public record ListRequest<T>(
        List<T> list
) {
}
