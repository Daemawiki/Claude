package com.daemawiki.external.database.redis.storage;

import reactor.core.publisher.Mono;

import java.time.Duration;

public interface RedisOperation<V> {

    Mono<V> getValue(String key);

    Mono<Boolean> hasKey(String key);

    Mono<Long> delete(String key);

    Mono<Boolean> save(String key, V value, Duration expiration);

}
