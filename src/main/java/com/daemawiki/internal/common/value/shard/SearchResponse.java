package com.daemawiki.internal.common.value.shard;

import com.daemawiki.internal.common.primitive.shard.PageNumber;
import com.daemawiki.internal.common.primitive.shard.SizeNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SearchResponse<T>(
        List<T> data,
        @JsonProperty("totalData")
        SizeNumber sizeNumber,
        @JsonProperty("currentPage")
        PageNumber pageNumber,
        boolean hasNextPage
) {

    public static <T> SearchResponse<T> create(
            final List<T> data,
            final SizeNumber sizeNumber,
            final PageNumber pageNumber,
            final boolean hasNextPage
    ) {
        return new SearchResponse<>(data, sizeNumber, pageNumber, hasNextPage);
    }

}
