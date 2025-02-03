package com.daemawiki.internal.core.domain.event.mail;

import com.daemawiki.internal.core.domain.model.event.mail.MailSendEvent;
import com.daemawiki.internal.core.domain.model.dto.mail.AuthCodeDTO;
import com.daemawiki.internal.data.repository.mail.AuthCodeRepository;
import com.daemawiki.internal.core.domain.model.primitive.mail.AuthCode;
import com.daemawiki.internal.data.property.MailSenderProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "AuthMailSendEventHandler")
class AuthMailSendEventHandler {

    private static final String MAIL_TITLE = "DSM 메일 인증";

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

    private final MailSenderProperties mailSenderProperties;

    private final AuthCodeRepository authCodeRepository;

    private final JavaMailSender mailSender;

    @Transactional
    @TransactionalEventListener(MailSendEvent.class)
    public void handle(final MailSendEvent event) {
        Mono.fromCallable(() -> {
                    final var authCodeModel = AuthCodeDTO.create(event.to(), AuthCode.create());

                    sendMail(authCodeModel);
                    return authCodeModel;
                })
                .flatMap(this::saveAuthCode)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        success -> log.info("Mail sent successfully - To: {}", event.to()),
                        error -> log.error("Mail sending error - To: {}, Error: {}", event.to(), error.getMessage())
                );
    }

    private void sendMail(final AuthCodeDTO dto) throws MessagingException, UnsupportedEncodingException {
        final MimeMessage message = mailSender.createMimeMessage();
        final var helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(dto.email().value());
        helper.setSubject(MAIL_TITLE);
        helper.setText(String.format(MAIL_TEMPLATE, dto.authCode().value()), true);
        helper.setFrom(new InternetAddress(mailSenderProperties.email(), MAIL_TITLE));

        mailSender.send(message);
    }

    private Mono<AuthCodeDTO> saveAuthCode(final AuthCodeDTO dto) {
        return authCodeRepository.save(dto)
                .thenReturn(dto)
                .onErrorMap(e -> e);
    }

}
