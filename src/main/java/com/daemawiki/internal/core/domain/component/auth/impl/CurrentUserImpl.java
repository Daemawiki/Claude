package com.daemawiki.internal.core.domain.component.auth.impl;

import com.daemawiki.internal.core.domain.component.auth.CurrentUser;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.data.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Component
@RequiredArgsConstructor
class CurrentUserImpl implements CurrentUser {

    private final UserRepository userRepository;

    @Override
    public Mono<UserInternalDTO> get() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .flatMap(email -> userRepository.findByEmail(Email.create(email)))
                .switchIfEmpty(Mono.error(CustomExceptionFactory.unauthorized("인증 정보를 불러오지 못했습니다.")));
    }

}
