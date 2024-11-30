package com.daemawiki.daemawiki.application.mail.service;

import com.daemawiki.daemawiki.application.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.application.mail.usecase.MailSendUseCase;
import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.common.util.random.AuthCodeGenerator;
import com.daemawiki.daemawiki.domain.mail.model.AuthCodeModel;
import com.daemawiki.daemawiki.domain.mail.model.type.MailType;
import com.daemawiki.daemawiki.domain.mail.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "유저 메일 전송 서비스")
class MailSendService implements MailSendUseCase {

    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @Override
    public Mono<Void> send(String to, MailType type) {
        return userRepository.findByEmail(to)
                .flatMap(user -> MailType.REGISTER.equals(type)
                        ? Mono.error(CustomExceptionFactory.conflict("이미 가입된 이메일입니다."))
                        : Mono.empty())
                .then(Mono.defer(() -> processSendMail(to)));
    }

    private Mono<Void> processSendMail(String to) {
        eventPublisher.publishEvent(new MailSendEvent(to));
        return Mono.empty();
    }
}