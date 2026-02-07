package com.daemawiki.internal.user.repository;

import com.daemawiki.internal.user.dto.UserInternalDTO;
import com.daemawiki.internal.user.primitive.UserId;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.primitive.personal.Generation;
import com.daemawiki.internal.user.primitive.personal.Major;
import com.daemawiki.internal.common.value.shard.PagingRequest;
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
