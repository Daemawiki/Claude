package com.daemawiki.external.database.redis.storage.impl;

import com.daemawiki.external.database.redis.storage.RedisOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
class RedisOperationImpl<V> implements RedisOperation<V> {

    private final ReactiveRedisOperations<String, V> redisOperations;

    @Override
    public Mono<V> getValue(final String key) {
        return redisOperations.opsForValue()
                .get(key);
    }

    @Override
    public Mono<Boolean> hasKey(final String key) {
        return redisOperations.hasKey(key);
    }

    @Override
    public Mono<Long> delete(final String key) {
        return redisOperations.delete(key);
    }

    @Override
    public Mono<Boolean> save(
            final String key,
            final V value,
            final Duration expiration
    ) {
        return redisOperations.opsForValue()
                .set(key, value, expiration);
    }

}
