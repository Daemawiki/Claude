package com.daemawiki.internal.user.repository;

import com.daemawiki.internal.user.dto.UserInternalDTO;
import com.daemawiki.internal.user.primitive.UserId;
import com.daemawiki.internal.user.primitive.personal.Email;
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
        return userMongoRepository.findByEmail(email.value())
                .map(userEntityMapper::toDTO);
    }

    @Override
    public Mono<Boolean> existsByEmail(final Email email) {
        return userMongoRepository.existsByEmail(email.value());
    }

    @Override
    public Mono<UserInternalDTO> findById(final UserId userId) {
        return userMongoRepository.findById(userId.value())
                .map(userEntityMapper::toDTO);
    }

}
