package com.daemawiki.internal.user;

import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface ManagerAddUseCase {

    Mono<Void> add(Email email);

}
