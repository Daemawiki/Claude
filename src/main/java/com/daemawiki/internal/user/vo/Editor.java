package com.daemawiki.internal.user.vo;

import com.daemawiki.internal.user.primitive.UserId;
import com.daemawiki.internal.user.primitive.personal.Name;
import com.daemawiki.internal.core.domain.model.value.ValueObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public record Editor(
        Name name,
        UserId userId
) implements ValueObject {

    private static final Cache<UserId, Editor> CACHE =
            Caffeine.newBuilder()
                    .maximumSize(100)
                    .expireAfterWrite(2, TimeUnit.HOURS)
                    .build();

    public static Editor create(
            final Name name,
            final UserId userId
    ) {
        return CACHE.get(
                userId,
                key -> {
                    final var value = new Editor(name, userId);
                    CACHE.put(userId, value);
                    return value;
                }
        );
    }

    public static Editor create(
            final UserId userId
    ) {
        return create(Name.emptyInstance(), userId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Editor editor = (Editor) obj;
        return Objects.equals(userId, editor.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

}
