package com.daemawiki.internal.core.domain.model.value.shard.date;

import com.daemawiki.internal.core.domain.model.primitive.shard.date.CreatedDateTime;
import com.daemawiki.internal.core.domain.model.primitive.shard.date.LastModifiedDateTime;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

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
