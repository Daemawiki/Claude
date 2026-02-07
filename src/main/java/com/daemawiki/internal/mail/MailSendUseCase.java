package com.daemawiki.internal.mail;

import com.daemawiki.internal.mail.primitive.MailType;
import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface MailSendUseCase {

    Mono<Void> send(
            Email to,
            MailType type
    );

}