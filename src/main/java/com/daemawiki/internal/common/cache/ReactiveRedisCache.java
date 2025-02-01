package com.daemawiki.internal.common.cache;

import com.daemawiki.external.database.redis.storage.RedisOperation;
import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import reactor.core.publisher.Mono;

import java.time.Duration;

class ReactiveRedisCache<K extends DomainPrimitive.StringDP, V> implements ReactiveCache<K, V> {

    private final RedisOperation<V> redisOperation;
    private final Duration expiration;
    private final String prefix;

    public ReactiveRedisCache(
            final RedisOperation<V> redisOperation,
            final Duration expiration,
            final String prefix
    ) {
        this.redisOperation = redisOperation;
        this.expiration = expiration;
        this.prefix = prefix;
    }

    @Override
    public Mono<V> get(final K key) {
        return redisOperation.getValue(prefix + key.value());
    }

    @Override
    public void put(
            final K key,
            final V value
    ) {
        redisOperation.save(prefix + key.value(), value, expiration)
                .subscribe();
    }

}
