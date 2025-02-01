package com.daemawiki.internal.core.usecase.auth;

import com.daemawiki.internal.core.domain.model.dto.auth.RegisterDTO;
import reactor.core.publisher.Mono;

public interface RegisterUseCase {

    Mono<Void> register(RegisterDTO dto);

}
