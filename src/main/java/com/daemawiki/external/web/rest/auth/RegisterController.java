package com.daemawiki.external.web.rest.auth;

import com.daemawiki.external.web.annotation.ui.AuthRestApi;
import com.daemawiki.external.web.rest.auth.dto.form.RegisterForm;
import com.daemawiki.internal.core.usecase.auth.RegisterUseCase;
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
