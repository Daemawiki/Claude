package com.daemawiki.internal.core.domain.component.auth;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import reactor.core.publisher.Mono;

public interface CurrentUser {

    Mono<UserInternalDTO> get();

}
