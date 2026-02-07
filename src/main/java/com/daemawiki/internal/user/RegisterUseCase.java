package com.daemawiki.internal.user;

import com.daemawiki.internal.user.dto.RegisterDTO;
import reactor.core.publisher.Mono;

public interface RegisterUseCase {

    Mono<Void> register(RegisterDTO dto);

}
