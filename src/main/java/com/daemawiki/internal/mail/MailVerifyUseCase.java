package com.daemawiki.internal.mail;

import com.daemawiki.internal.mail.primitive.AuthCode;
import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface MailVerifyUseCase {

    Mono<Void> verify(
            Email target,
            AuthCode authCode
    );

}
