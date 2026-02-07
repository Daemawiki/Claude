package com.daemawiki.internal.user.service;

import com.daemawiki.internal.user.ManagerAddUseCase;
import com.daemawiki.internal.user.dto.ManagerInternalDTO;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.repository.ManagerRepository;
import com.daemawiki.internal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class ManagerAddService implements ManagerAddUseCase {

    private final ManagerRepository managerRepository;

    private final UserRepository userRepository;

    @Override
    public Mono<Void> add(final Email email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> userRepository.save(user.updateRoleToManager()))
                .map(user -> ManagerInternalDTO.create(user.personalData().email(), user.userId()))
                .switchIfEmpty(Mono.defer(() -> Mono.just(ManagerInternalDTO.create(email)))) // TODO: 2/5/25 의도대로 작동하는 지 확인할 것.
                .flatMap(managerRepository::save)
                .then();
    }

}