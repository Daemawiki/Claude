package com.daemawiki.external.web.rest.auth;

import com.daemawiki.external.web.rest.auth.dto.RegisterForm;
import com.daemawiki.internal.core.usecase.auth.LoginUseCase;
import com.daemawiki.internal.core.usecase.auth.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
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
