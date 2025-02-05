package com.daemawiki.internal.core.domain.service.auth;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.external.security.paseto.Tokenizer;
import com.daemawiki.external.web.rest.auth.dto.LoginResponse;
import com.daemawiki.internal.core.domain.model.dto.auth.LoginDTO;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.auth.Password;
import com.daemawiki.internal.core.usecase.auth.LoginUseCase;
import com.daemawiki.internal.data.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final Tokenizer tokenizer;

    @Override
    public Mono<LoginResponse> login(
            final LoginDTO dto
    ) {
        return loginProcess(dto)
                .flatMap(user -> tokenizer.generate(user.personalData().email().value())
                            .map(token -> LoginResponse.create(token, user.personalData().name(), user.userRole())));
    }

    private Mono<UserInternalDTO> loginProcess(final LoginDTO dto) {
        return userRepository.findByEmail(dto.email())
                .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound(dto.email().value() + "의 메일 주소를 사용하는 사용자를 찾지 못했습니다.")))
                .flatMap(user -> validatePassword(user, dto.password()));
    }

    private Mono<UserInternalDTO> validatePassword(
            final UserInternalDTO user,
            final Password requestPassword
    ) {
        return passwordEncoder.matches(requestPassword.password(), user.personalData().securedPassword().value())
                ? Mono.just(user)
                : Mono.error(CustomExceptionFactory.unauthorized("비밀번호가 일치하지 않습니다."));
    }

}
