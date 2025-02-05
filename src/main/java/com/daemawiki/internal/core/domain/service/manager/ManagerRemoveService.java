package com.daemawiki.internal.core.domain.service.manager;

import com.daemawiki.internal.core.usecase.manager.ManagerRemoveUseCase;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.manager.ManagerInternalDTO;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Email;
import com.daemawiki.internal.data.repository.manager.ManagerRepository;
import com.daemawiki.internal.data.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class ManagerRemoveService implements ManagerRemoveUseCase {

    private final ManagerRepository managerRepository;

    private final UserRepository userRepository;

    @Override
    public Mono<Void> remove(final Email email) {
        return managerRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.badRequest("해당 이메일은 관리자가 아닙니다.")))
                .filter(manager -> manager.userId() == null || manager.userId().value().isBlank())
                .flatMap(this::lossOfRole)
                .zipWhen(manager -> managerRepository.delete(email)) // TODO: 2/5/25 의도대로 작동되는 지 확인. (병럴 실행)
                .then();
    }

    private Mono<ManagerInternalDTO> lossOfRole(final ManagerInternalDTO managerDto) {
        return userRepository.findById(managerDto.userId())
                .map(UserInternalDTO::updateRoleToMop)
                .flatMap(userRepository::save)
                .thenReturn(managerDto);
    }

}
