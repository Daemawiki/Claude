package com.daemawiki.internal.core.usecase.manager;

import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Mono;

public interface ManagerRemoveUseCase {

    Mono<Void> remove(Email email);

}
