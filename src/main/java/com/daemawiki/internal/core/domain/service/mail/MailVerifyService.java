package com.daemawiki.internal.core.domain.service.mail;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.primitive.mail.AuthCode;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.core.usecase.mail.MailVerifyUseCase;
import com.daemawiki.internal.data.repository.mail.AuthCodeRepository;
import com.daemawiki.internal.data.repository.mail.AuthMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class MailVerifyService implements MailVerifyUseCase {

    private final AuthCodeRepository authCodeRepository;

    private final AuthMailRepository authMailRepository;

    @Override
    public Mono<Void> verify(
            final Email target,
            final AuthCode authCode
    ) {
        return authCodeRepository.findByMail(target)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.badRequest("인증에 실패하였습니다. 인증 코드를 다시 보내세요.")))
                .filter(dto -> dto.authCode().equals(authCode))
                .switchIfEmpty(Mono.error(CustomExceptionFactory.badRequest("인증 코드가 정확하지 않습니다.")))
                .flatMap(dto -> process(dto.email()));
    }

    private Mono<Void> process(final Email email) {
        return Mono.when(
                authMailRepository.save(email),
                authCodeRepository.deleteByEmail(email)
        );
    }

}
