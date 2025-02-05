package com.daemawiki.internal.core.domain.model.value.document;

import com.daemawiki.internal.core.domain.model.primitive.shard.info.Version;
import com.daemawiki.internal.core.domain.model.primitive.shard.info.ViewCount;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

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
