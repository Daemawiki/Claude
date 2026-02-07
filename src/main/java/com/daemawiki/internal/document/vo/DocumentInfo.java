package com.daemawiki.internal.document.vo;

import com.daemawiki.internal.common.primitive.shard.Version;
import com.daemawiki.internal.common.primitive.shard.ViewCount;
import com.daemawiki.internal.common.value.ValueObject;

public record DocumentInfo(
        ViewCount viewCount,
        Version version
) implements ValueObject {

    public static DocumentInfo create(
            final ViewCount viewCount,
            final Version version
    ) {
        return new DocumentInfo(viewCount, version);
    }

    public static DocumentInfo createEmpty() {
        return create(
                ViewCount.create(0L),
                Version.create(0L)
        );
    }

}
