package com.daemawiki.internal.common.value.shard;

import com.daemawiki.internal.common.primitive.shard.CreatedDateTime;
import com.daemawiki.internal.common.primitive.shard.LastModifiedDateTime;
import com.daemawiki.internal.common.value.ValueObject;

import java.time.LocalDateTime;

public record EditedDateTime(
        CreatedDateTime createdDateTime,
        LastModifiedDateTime lastModifiedDateTime
) implements ValueObject {

    public static EditedDateTime create(
            final CreatedDateTime createdDateTime,
            final LastModifiedDateTime lastModifiedDateTime
    ) {
        return new EditedDateTime(createdDateTime, lastModifiedDateTime);
    }

    public static EditedDateTime createEmpty() {
        final var now = LocalDateTime.now();

        return create(
                CreatedDateTime.create(now),
                LastModifiedDateTime.create(now)
        );
    }

}
