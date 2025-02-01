package com.daemawiki.internal.data.repository.user;

import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.UserId;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
abstract class UserAbstractRepository implements UserRepository {

    private final UserMongoRepository userMongoRepository;
    protected final UserEntityMapper userEntityMapper;

    @Override
    public Mono<UserInternalDTO> save(final UserInternalDTO dto) {
        final UserEntity entity = userEntityMapper.toEntity(dto);

        return userMongoRepository.save(entity)
                .map(userEntityMapper::toDTO);
    }

    @Override
    public Mono<UserInternalDTO> findByEmail(final Email email) {
        return userMongoRepository.findByEmail(email.email())
                .map(userEntityMapper::toDTO);
    }

    @Override
    public Mono<Boolean> existsByEmail(final Email email) {
        return userMongoRepository.existsByEmail(email.email());
    }

    @Override
    public Mono<UserInternalDTO> findById(final UserId userId) {
        return userMongoRepository.findById(userId.userId())
                .map(userEntityMapper::toDTO);
    }

}
