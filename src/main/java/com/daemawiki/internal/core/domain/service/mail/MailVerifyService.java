package com.daemawiki.internal.core.domain.service.mail;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.core.usecase.mail.MailVerifyUseCase;
import com.daemawiki.internal.core.domain.model.dto.mail.AuthCodeDTO;
import com.daemawiki.archive.daemawiki.domain.mail.repository.AuthCodeRepository;
import com.daemawiki.archive.daemawiki.domain.mail.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class MailVerifyService implements MailVerifyUseCase {

    private final AuthCodeRepository authCodeRepository;

    private final AuthUserRepository authUserRepository;

    @Override
    public Mono<Void> verify(
            final Email target,
            final String code
    ) {
        return authCodeRepository.findByMail(target)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.badRequest("인증에 실패하였습니다. 인증 코드를 다시 보내세요.")))
                .filter(model -> model.code().equals(code))
                .switchIfEmpty(Mono.error(CustomExceptionFactory.badRequest("인증 코드가 정확하지 않습니다.")))
                .flatMap(this::saveAuthenticationUser);
    }

    private Mono<Void> saveAuthenticationUser(final AuthCodeDTO model) { // TODO: 1/31/25 리팩토링 대상 메서드
        return authUserRepository.save(model.email()).then(); // 불필요한 깊이를 만드는 메서드
    }

}
