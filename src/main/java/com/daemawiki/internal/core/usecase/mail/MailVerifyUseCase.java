package com.daemawiki.internal.core.usecase.mail;

import com.daemawiki.internal.core.domain.model.primitive.mail.AuthCode;
import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface MailVerifyUseCase {

    Mono<Void> verify(
            Email target,
            AuthCode authCode
    );

}
