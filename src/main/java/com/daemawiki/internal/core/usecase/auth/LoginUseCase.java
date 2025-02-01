package com.daemawiki.internal.core.usecase.auth;

import com.daemawiki.internal.core.domain.model.dto.auth.LoginDTO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface LoginUseCase {

    Mono<String> login(LoginDTO dto, ServerHttpRequest serverHttpRequest);

}
