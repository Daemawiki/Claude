package com.daemawiki.internal.mail.service;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.mail.MailSendUseCase;
import com.daemawiki.internal.mail.event.MailSendEvent;
import com.daemawiki.internal.mail.primitive.MailType;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "MailSendService")
class MailSendService implements MailSendUseCase {

    private final ApplicationEventPublisher eventPublisher;

    private final UserRepository userRepository;

    @Override
    public Mono<Void> send(
            final Email to,
            final MailType type
    ) {
        return userRepository.findByEmail(to)
                .flatMap(user -> MailType.REGISTER.equals(type)
                        ? Mono.error(CustomExceptionFactory.conflict("이미 가입된 이메일입니다."))
                        : Mono.empty())
                .doOnSuccess(o -> eventPublisher.publishEvent(MailSendEvent.create(to)))
                .then();
    }

}