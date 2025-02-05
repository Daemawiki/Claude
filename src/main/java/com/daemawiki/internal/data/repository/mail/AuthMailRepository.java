package com.daemawiki.internal.data.repository.mail;

import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Mono;

public interface AuthMailRepository {

    Mono<Boolean> save(Email mail);

    Mono<Boolean> existsByEmail(Email mail);

    Mono<Void> delete(Email mail);

}
