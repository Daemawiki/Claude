package com.daemawiki.internal.common.cache;

import com.daemawiki.external.database.redis.storage.RedisOperation;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@EnableCaching
@Configuration
class CacheConfiguration {

    @Bean
    CacheManager cacheManager() {
        final var cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(Duration.ofHours(1))
                .recordStats());

        return cacheManager;
    }

    @Bean("DOCUMENT_REDIS_CACHE")
    ReactiveCache<DocumentId, DocumentInternalDTO> documentRedisCache(final RedisOperation<DocumentInternalDTO> redisOperation) {
        return new ReactiveRedisCache<>(redisOperation, Duration.ofHours(1), "DocumentId*");
    }

}
