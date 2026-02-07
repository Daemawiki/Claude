package com.daemawiki.internal.user.component;

import com.daemawiki.internal.user.dto.UserInternalDTO;
import reactor.core.publisher.Mono;

public interface CurrentUser {

    Mono<UserInternalDTO> get();

}
