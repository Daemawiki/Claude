package com.daemawiki.internal.data.repository.mail.impl;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.data.repository.mail.AuthMailRepository;
import com.daemawiki.external.database.redis.RedisKey;
import com.daemawiki.external.database.redis.storage.RedisOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "AuthMailRepositoryImpl")
class AuthMailRepositoryImpl implements AuthMailRepository {
    private static final String AUTH_MAIL = RedisKey.AUTH_USER.getKey();

    private final RedisOperation<Email> redisOperation;

    @Override
    public Mono<Boolean> save(final Email mail) {
        return handleError(redisOperation.save(
                AUTH_MAIL + mail,
                mail,
                Duration.ofHours(1)
        ));
    }

    @Override
    public Mono<Boolean> existsByEmail(final Email mail) {
        return handleError(redisOperation.hasKey(AUTH_MAIL + mail));
    }

    @Override
    public Mono<Void> delete(final Email mail) {
        return handleError(redisOperation.delete(AUTH_MAIL + mail).then());
    }

    private static <T> Mono<T> handleError(final Mono<T> mono) {
        return mono.onErrorMap(e -> {
            log.error("#- Error: " + e);
            return e instanceof RedisConnectionFailureException ?
                    CustomExceptionFactory.internalServerError(
                            "레디스 서버에 연결하는데 문제가 발생했습니다."
                    ) : e;
        });
    }
}
