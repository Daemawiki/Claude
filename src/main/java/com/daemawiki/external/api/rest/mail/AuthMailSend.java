package com.daemawiki.external.api.rest.mail;

import com.daemawiki.external.api.annotation.MailRestApi;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.mail.event.MailSendEvent;
import com.daemawiki.internal.mail.primitive.MailType;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@MailRestApi
@RequiredArgsConstructor
class AuthMailSend {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @PostMapping("/send")
    Mono<Void> send(
            @RequestParam("target")
            @JsonProperty("target")
            Email target,
            @RequestParam("type") MailType type
    ) {
        return userRepository.findByEmail(target)
                .flatMap(user -> MailType.REGISTER.equals(type)
                        ? Mono.error(CustomExceptionFactory.conflict("이미 가입된 이메일입니다."))
                        : Mono.empty())
                .doOnSuccess(o -> eventPublisher.publishEvent(MailSendEvent.create(target)))
                .then();
    }
}
