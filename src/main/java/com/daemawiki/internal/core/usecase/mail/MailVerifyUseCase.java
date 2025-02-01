package com.daemawiki.internal.core.usecase.mail;

import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Mono;

public interface MailVerifyUseCase {

    Mono<Void> verify(
            Email target,
            String code
    );

}
