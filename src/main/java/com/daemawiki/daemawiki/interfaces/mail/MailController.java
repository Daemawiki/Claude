package com.daemawiki.daemawiki.interfaces.mail;

import com.daemawiki.daemawiki.application.mail.usecase.MailSendUseCase;
import com.daemawiki.daemawiki.application.mail.usecase.MailVerifyUseCase;
import com.daemawiki.daemawiki.domain.mail.model.type.MailType;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
class MailController {
    private final MailVerifyUseCase userMailVerifyUseCase;
    private final MailSendUseCase userMailSendUseCase;

    @PostMapping("/send")
    Mono<Void> send(
            @RequestParam("target") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(dsm\\.hs\\.kr)$",
                    message = "메일 형식이 올바르지 않습니다.") String target,
            @RequestParam("type") MailType type
    ) {
        return userMailSendUseCase.send(target, type);
    }

    @PostMapping("/verify")
    Mono<Void> verify(
            @RequestParam("target") String target,
            @RequestParam("code") String code
    ) {
        return userMailVerifyUseCase.verify(target, code);
    }
}
