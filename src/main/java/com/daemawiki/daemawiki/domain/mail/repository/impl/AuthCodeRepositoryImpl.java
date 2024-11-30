package com.daemawiki.daemawiki.domain.mail.repository.impl;

import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.domain.mail.model.AuthCodeModel;
import com.daemawiki.daemawiki.domain.mail.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.infrastructure.redis.RedisKey;
import com.daemawiki.daemawiki.infrastructure.redis.storage.RedisOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "메일 인증 코드 레디스 레포지토리")
class AuthCodeRepositoryImpl implements AuthCodeRepository {
    private static final String AUTH_CODE = RedisKey.AUTH_CODE.getKey();

    private final RedisOperation redisOperation;

    @Override
    public Mono<Boolean> save(AuthCodeModel model) {
        return handleError(redisOperation.save(
                AUTH_CODE + model.email(),
                model.code(),
                Duration.ofMinutes(30)
        ));
    }

    @Override
    public Mono<AuthCodeModel> findByMail(String email) {
        return handleError(redisOperation.getValue(AUTH_CODE + email)
                .map(code -> AuthCodeModel.of(email, code)));
    }

    @Override
    public Mono<Void> delete(AuthCodeModel model) {
        return deleteByEmail(model.email()).then();
    }

    @Override
    public Mono<Long> deleteByEmail(String email) {
        return handleError(redisOperation.delete(AUTH_CODE + email));
    }

    private static <T> Mono<T> handleError(Mono<T> mono) {
        return mono.onErrorMap(e -> {
            log.error("#- Error: " + e);
            return CustomExceptionFactory.internalServerError("레디스 서버에 연결하는데 문제가 발생했습니다.");
        });
    }
}
