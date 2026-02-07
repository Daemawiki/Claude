package com.daemawiki.internal.core.usecase.mail;

import com.daemawiki.internal.core.domain.model.primitive.mail.MailType;
import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface MailSendUseCase {

    Mono<Void> send(
            Email to,
            MailType type
    );

}