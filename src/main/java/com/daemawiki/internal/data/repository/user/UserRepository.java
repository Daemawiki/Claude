package com.daemawiki.internal.data.repository.user;

import com.daemawiki.internal.common.paging.PagingInfo;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Generation;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Major;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<UserInternalDTO> save(UserInternalDTO dto);

    Mono<UserInternalDTO> findByEmail(Email email);

    Flux<UserInternalDTO> findByGenerationAndMajor(
            Generation generation,
            Major major,
            PagingInfo pagingInfo
    );

    Mono<Boolean> existsByEmail(Email email);

    Mono<UserInternalDTO> findById(UserId userId);

}
