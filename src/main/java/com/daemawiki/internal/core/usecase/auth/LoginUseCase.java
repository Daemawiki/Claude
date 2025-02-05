package com.daemawiki.internal.core.usecase.auth;

import com.daemawiki.external.web.rest.auth.dto.LoginResponse;
import com.daemawiki.internal.core.domain.model.dto.auth.LoginDTO;
import reactor.core.publisher.Mono;

public interface LoginUseCase {

    Mono<LoginResponse> login(LoginDTO dto);

}
