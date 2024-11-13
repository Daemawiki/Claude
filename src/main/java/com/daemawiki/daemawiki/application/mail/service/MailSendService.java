package com.daemawiki.daemawiki.application.mail.service;

import com.daemawiki.daemawiki.application.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.application.mail.usecase.MailSendUseCase;
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
    private static final String MAIL_TEMPLATE =
            "<div style='margin: 0; padding: 40px; background-color: #f4f4f8; font-family: Arial, sans-serif;'>"
                    + "<table align='center' cellpadding='0' cellspacing='0' width='460px' style='background-color: #ffffff; border: 1px solid #ebebeb; border-radius: 16px; box-shadow: 0px 8px 16px rgba(100, 100, 100, 0.1); padding: 32px;'>"
                    + "<tr>"
                    + "<td align='center' style='padding-bottom: 24px;'>"
                    + "<a href='https://imgbb.com/'>"
                    + "<img src='https://i.ibb.co/R7kTKcZ/daemawiki-logo.png' alt='DaemaWiki Logo' width='120' style='border: 0; margin-bottom: 16px;' />"
                    + "</a>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='center' style='padding-bottom: 16px;'>"
                    + "<p style='font-size: 14px; margin: 0; color: #7d7d7d;'>DSM 이메일 인증 코드</p>"
                    + "<div style='padding-bottom: 4px; border-bottom: 1px solid #ebebeb;'>"
                    + "<p style='font-size: 40px; font-weight: bold; color: #2c3e50; margin: 12px 0;'>%s</p>"
                    + "</div>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='center'>"
                    + "<p style='font-size: 14px; color: #999; margin: 0;'>"
                    + "<span style='color: #84cc16;'>*</span> 인증 코드는 30분 동안 유효합니다."
                    + "</p>"
                    + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "</div>";

    private static final int CODE_LENGTH = 6;

    private final ApplicationEventPublisher eventPublisher;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeGenerator authCodeGenerator;
    private final UserRepository userRepository;

    @Override
    public Mono<Void> send(String to, MailType type) {
        return userRepository.findByEmail(to)
                .flatMap(user -> MailType.REGISTER.equals(type)
                        ? Mono.error(new RuntimeException()) // 이메일이 이미 사용 중일 때
                        : Mono.empty())
                .then(Mono.defer(() -> processSendMail(to)));
    }

    private Mono<Void> processSendMail(String to) {
        return saveAuthCode(AuthCodeModel.of(to, authCodeGenerator.generate(CODE_LENGTH)))
                .doOnNext(authCodeModel -> log.info("authCode: {} to: {}", authCodeModel.code(), authCodeModel.email()))
                .map(authCodeModel -> new MailSendEvent(authCodeModel.email(), String.format(MAIL_TEMPLATE, authCodeModel.code())))
                .doOnSuccess(eventPublisher::publishEvent)
                .then();
    }

    private Mono<AuthCodeModel> saveAuthCode(AuthCodeModel authCodeModel) {
        return authCodeRepository.save(authCodeModel)
                .thenReturn(authCodeModel)
                .onErrorMap(e -> e);
    }
}