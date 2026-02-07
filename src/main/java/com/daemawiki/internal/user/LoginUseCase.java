package com.daemawiki.internal.user;

import com.daemawiki.external.api.rest.auth.dto.LoginResponse;
import com.daemawiki.internal.user.dto.LoginDTO;
import reactor.core.publisher.Mono;

public interface LoginUseCase {

    Mono<LoginResponse> login(LoginDTO dto);

}
