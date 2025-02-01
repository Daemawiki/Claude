package com.daemawiki.archive.daemawiki.domain.mail.repository.impl;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.mail.AuthCodeDTO;
import com.daemawiki.archive.daemawiki.domain.mail.repository.AuthCodeRepository;
import com.daemawiki.external.database.redis.RedisKey;
import com.daemawiki.external.database.redis.storage.RedisOperation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
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
    public Mono<Boolean> save(final AuthCodeDTO model) {
        return handleError(redisOperation.save(
                AUTH_CODE + model.email(),
                model.code(),
                Duration.ofMinutes(30)
        ));
    }

    @Override
    public Mono<AuthCodeDTO> findByMail(final Email email) {
        return handleError(redisOperation.getValue(AUTH_CODE + email.value())
                .map(code -> AuthCodeDTO.of(email.value(), code)));
    }

    @Override
    public Mono<Long> deleteByEmail(final Email email) {
        return handleError(redisOperation.delete(AUTH_CODE + email.value()));
    }

    private static <T> Mono<T> handleError(final Mono<T> mono) {
        return mono.onErrorMap(e -> {
            log.error("#- Error: " + e);
            return CustomExceptionFactory.internalServerError("레디스 서버에 연결하는데 문제가 발생했습니다.");
        });
    }

}
