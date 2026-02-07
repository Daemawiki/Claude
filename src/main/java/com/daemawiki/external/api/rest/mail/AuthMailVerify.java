package com.daemawiki.external.api.rest.mail;

import com.daemawiki.external.api.annotation.MailRestApi;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.mail.primitive.AuthCode;
import com.daemawiki.internal.mail.repository.AuthCodeRepository;
import com.daemawiki.internal.mail.repository.AuthMailRepository;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@MailRestApi
@RequiredArgsConstructor
class AuthMailVerify {
    private final AuthCodeRepository authCodeRepository;
    private final AuthMailRepository authMailRepository;

    @PostMapping("/verify")
    Mono<Void> verify(
            @RequestParam("target")
            @JsonProperty("target")
            Email target,
            @RequestParam("code")
            @JsonProperty("code")
            AuthCode code
    ) {
        return authCodeRepository.findByMail(target)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.badRequest("인증에 실패하였습니다. 인증 코드를 다시 보내세요.")))
                .filter(dto -> dto.authCode().equals(code))
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
