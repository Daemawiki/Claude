package com.daemawiki.external.api.rest.mail;

import com.daemawiki.external.api.annotation.MailRestApi;
import com.daemawiki.internal.mail.primitive.AuthCode;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@MailRestApi
@RequiredArgsConstructor
class MailVerifyController {

    private final MailVerifyUseCase userMailVerifyUseCase;

    @PostMapping("/verify")
    Mono<Void> verify(
            @RequestParam("target")
            @JsonProperty("target")
            Email target,

            @RequestParam("code")
            @JsonProperty("code")
            AuthCode code
    ) {
        return userMailVerifyUseCase.verify(target, code);
    }

}
