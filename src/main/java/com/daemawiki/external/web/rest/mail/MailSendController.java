package com.daemawiki.external.web.rest.mail;

import com.daemawiki.external.web.annotation.ui.MailRestApi;
import com.daemawiki.internal.mail.MailSendUseCase;
import com.daemawiki.internal.mail.primitive.MailType;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@MailRestApi
@RequiredArgsConstructor
class MailSendController {

    private final MailSendUseCase userMailSendUseCase;

    @PostMapping("/send")
    Mono<Void> send(
            @RequestParam("target")
            @JsonProperty("target")
            Email target,
            @RequestParam("type") MailType type
    ) {
        return userMailSendUseCase.send(target, type);
    }

}
