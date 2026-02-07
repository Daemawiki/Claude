package com.daemawiki.internal.mail.repository;

import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface AuthMailRepository {

    Mono<Boolean> save(Email mail);

    Mono<Boolean> existsByEmail(Email mail);

    Mono<Void> delete(Email mail);

}
