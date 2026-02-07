package com.daemawiki.internal.user;

import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Mono;

public interface ManagerRemoveUseCase {

    Mono<Void> remove(Email email);

}
