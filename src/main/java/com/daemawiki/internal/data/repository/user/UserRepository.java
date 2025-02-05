package com.daemawiki.internal.data.repository.user;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Generation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Major;
import com.daemawiki.internal.core.domain.model.value.shard.paging.PagingRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<UserInternalDTO> save(UserInternalDTO dto);

    Mono<UserInternalDTO> findByEmail(Email email);

    Flux<UserInternalDTO> findByGenerationAndMajor(
            Generation generation,
            Major major,
            PagingRequest pagingRequest
    );

    Mono<Boolean> existsByEmail(Email email);

    Mono<UserInternalDTO> findById(UserId userId);

}
