package com.daemawiki.internal.user.repository;

import com.daemawiki.internal.user.dto.ManagerInternalDTO;
import com.daemawiki.internal.user.primitive.personal.Email;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManagerRepository {

    Mono<ManagerInternalDTO> save(ManagerInternalDTO dto);

    Flux<ManagerInternalDTO> findAll();

    Mono<ManagerInternalDTO> findByEmail(Email email);

    Mono<Void> delete(Email email);

}
