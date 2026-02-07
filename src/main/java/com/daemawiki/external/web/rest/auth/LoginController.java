package com.daemawiki.external.web.rest.auth;

import com.daemawiki.external.web.annotation.ui.AuthRestApi;
import com.daemawiki.external.web.rest.auth.dto.LoginResponse;
import com.daemawiki.external.web.rest.auth.dto.form.LoginForm;
import com.daemawiki.internal.user.dto.LoginDTO;
import com.daemawiki.internal.user.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@AuthRestApi
@RequiredArgsConstructor
class LoginController {

    private final LoginUseCase loginUseCase;

    private final AuthDTOMapper dtoMapper;

    @PostMapping("/login")
    Mono<LoginResponse> login(
            @RequestBody final LoginForm loginForm
    ) {
        final LoginDTO dto = dtoMapper.toLoginDTO(loginForm);

        return loginUseCase.login(dto);
    }

}
