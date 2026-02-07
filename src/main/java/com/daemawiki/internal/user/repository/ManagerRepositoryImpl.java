package com.daemawiki.internal.user.repository;

import com.daemawiki.internal.user.dto.ManagerInternalDTO;
import com.daemawiki.internal.user.primitive.personal.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
class ManagerRepositoryImpl implements ManagerRepository {

    private final ManagerMongoRepository managerMongoRepository;

    private final ManagerEntityMapper managerEntityMapper;

    @Override
    public Mono<ManagerInternalDTO> save(final ManagerInternalDTO dto) {
        final ManagerEntity entity = managerEntityMapper.toEntity(dto);

        return managerMongoRepository.save(entity)
                .map(managerEntityMapper::toDTO);
    }

    @Override
    public Flux<ManagerInternalDTO> findAll() {
        return managerMongoRepository.findAll()
                .map(managerEntityMapper::toDTO);
    }

    @Override
    public Mono<ManagerInternalDTO> findByEmail(final Email email) {
        return managerMongoRepository.findByEmail(email.value())
                .map(managerEntityMapper::toDTO);
    }

    @Override
    public Mono<Void> delete(final Email email) {
        return managerMongoRepository.deleteByEmail(email.value());
    }

}
