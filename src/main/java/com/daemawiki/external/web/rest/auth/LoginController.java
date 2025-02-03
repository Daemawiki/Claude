package com.daemawiki.external.web.rest.auth;

import com.daemawiki.external.web.rest.auth.dto.form.LoginForm;
import com.daemawiki.internal.core.usecase.auth.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
class LoginController {

    private static final Duration COOKIE_EXPIRATION = Duration.ofHours(3);

    private final LoginUseCase loginUseCase;
    private final AuthDTOMapper dtoMapper;

    @PostMapping("/login")
    Mono<Void> login(
            @RequestBody final LoginForm loginForm,
            final ServerHttpResponse serverHttpResponse,
            final ServerHttpRequest serverHttpRequest
    ) {
        final var dto = dtoMapper.toLoginDTO(loginForm);

        return loginUseCase.login(dto, serverHttpRequest)
                .map(sessionValue -> ResponseCookie.from("SESSION_ID", sessionValue)
                        .path("/api")
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("NONE")
                        .maxAge(COOKIE_EXPIRATION)
                        .build())
                .doOnNext(serverHttpResponse::addCookie)
                .then();
    }

}
