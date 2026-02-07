package com.daemawiki.external.api.rest.auth;

import com.daemawiki.external.api.annotation.AuthRestApi;
import com.daemawiki.external.api.rest.auth.dto.RegisterForm;
import com.daemawiki.internal.user.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@AuthRestApi
@RequiredArgsConstructor
class RegisterController {

    private final RegisterUseCase userRegisterUseCase;

    private final AuthDTOMapper dtoMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> register(@RequestBody final RegisterForm registerForm) {
        final var dto = dtoMapper.toRegisterDTO(registerForm);

        return userRegisterUseCase.register(dto);
    }

}
