package com.daemawiki.internal.common.cache;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class ReactiveCaffeineCache<K extends DomainPrimitive.StringDP, V>  implements ReactiveCache<K, V> {

    private final Cache<String, V> cache;

    public ReactiveCaffeineCache(
            final int size,
            final int duration,
            final TimeUnit timeUnit
    ) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(size)
                .expireAfterWrite(duration, timeUnit)
                .build();
    }

    @Override
    public Mono<V> get(final K key) {
        return Mono.justOrEmpty(cache.getIfPresent(key.value()));
    }

    @Override
    public void put(
            final K key,
            final V value
    ) {
        cache.put(key.value(), value);
    }

}
