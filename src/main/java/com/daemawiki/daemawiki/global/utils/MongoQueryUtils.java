package com.daemawiki.daemawiki.global.utils;

import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public interface MongoQueryUtils {
    <T> Flux<T> find(Query query, Class<T> targetClass);

}
