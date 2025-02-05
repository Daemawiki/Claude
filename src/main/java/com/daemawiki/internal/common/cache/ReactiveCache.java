package com.daemawiki.internal.common.cache;

import com.daemawiki.internal.core.domain.model.primitive.DomainPrimitive;
import reactor.core.publisher.Mono;

public interface ReactiveCache<K extends DomainPrimitive, V> {

    Mono<V> get(K key);

    void put(K key, V value);

}
