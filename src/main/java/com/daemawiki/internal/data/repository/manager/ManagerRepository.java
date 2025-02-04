package com.daemawiki.internal.data.repository.manager;

import com.daemawiki.internal.core.domain.model.dto.manager.ManagerInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManagerRepository {

    Mono<ManagerInternalDTO> save(ManagerInternalDTO dto);

    Flux<ManagerInternalDTO> findAll();

    Mono<ManagerInternalDTO> findByEmail(Email email);

    Mono<Void> delete(Email email);

}
