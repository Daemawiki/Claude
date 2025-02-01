package com.daemawiki.internal.core.domain.service.mail;

import com.daemawiki.internal.core.domain.model.event.mail.MailSendEvent;
import com.daemawiki.internal.core.usecase.mail.MailSendUseCase;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.archive.daemawiki.domain.mail.model.type.MailType;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.data.repository.user.UserRepository;
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