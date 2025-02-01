package com.daemawiki.internal.core.domain.service.auth;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.auth.LoginDTO;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.auth.Password;
import com.daemawiki.internal.core.usecase.auth.LoginUseCase;
import com.daemawiki.archive.daemawiki.security.session.model.SessionModel;
import com.daemawiki.archive.daemawiki.security.session.repository.SessionRepository;
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

    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Mono<String> login(
            final LoginDTO dto,
            final ServerHttpRequest serverHttpRequest
    ) {
        return Mono.justOrEmpty(serverHttpRequest.getRemoteAddress())
                .switchIfEmpty(Mono.error(CustomExceptionFactory.forbidden("현재 접속중인 네트워크가 불안정합니다.")))
                .flatMap(remoteAddress -> createSession(dto, remoteAddress));
    }

    private Mono<String> createSession(
            final LoginDTO dto,
            final InetSocketAddress remoteAddress
    ) {
        return loginProcess(dto)
                .map(user -> SessionModel.of(remoteAddress.toString(), user.personalData().email().email()))
                .flatMap(sessionRepository::save)
                .map(SessionModel::id);
    }

    private Mono<UserInternalDTO> loginProcess(final LoginDTO dto) {
        return userRepository.findByEmail(dto.email())
                .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound(dto.email().email() + "의 메일 주소를 사용하는 사용자를 찾지 못했습니다.")))
                .flatMap(user -> validatePassword(user, dto.password()));
    }

    private Mono<UserInternalDTO> validatePassword(
            final UserInternalDTO user,
            final Password requestPassword
    ) {
        return passwordEncoder.matches(requestPassword.password(), user.personalData().securedPassword().securedPassword())
                ? Mono.just(user)
                : Mono.error(CustomExceptionFactory.unauthorized("비밀번호가 일치하지 않습니다."));
    }

}
