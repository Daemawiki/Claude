package com.daemawiki.internal.core.usecase.mail;

import com.daemawiki.archive.daemawiki.domain.mail.model.type.MailType;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Mono;

public interface MailSendUseCase {

    Mono<Void> send(
            Email to,
            MailType type
    );

}