package com.daemawiki.internal.user.service;

import com.daemawiki.internal.user.dto.RegisterDTO;
import com.daemawiki.internal.user.dto.ManagerInternalDTO;
import com.daemawiki.internal.user.dto.UserInternalDTO;
import com.daemawiki.internal.document.event.DocumentCreateByUserEvent;
import com.daemawiki.internal.user.primitive.UserRole;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.RegisterUseCase;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.data.repository.mail.AuthMailRepository;
import com.daemawiki.internal.user.repository.ManagerRepository;
import com.daemawiki.internal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
class RegisterService implements RegisterUseCase {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final AuthMailRepository authMailRepository;

    private final ManagerRepository managerRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public Mono<Void> register(final RegisterDTO dto) {
        return validateRegistration(dto.email())
                .then(Mono.defer(() -> executeRegisterProcess(dto)));
    }

    private Mono<Void> validateRegistration(final Email email) {
        return Mono.when(
                ensureEmailNotRegistered(email),
                validateEmailAuthentication(email)
        );
    }

    private Mono<Void> ensureEmailNotRegistered(final Email email) {
        return userRepository.existsByEmail(email)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.conflict(email + "로 가입된 유저가 이미 존재합니다.")))
                .then();
    }

    private Mono<Void> validateEmailAuthentication(final Email email) {
        return authMailRepository.existsByEmail(email)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound("인증 정보를 불러오지 못했습니다.")))
                .then();
    }

    private Mono<Void> executeRegisterProcess(final RegisterDTO dto) {
        return createUserWithRole(dto)
                .map(DocumentCreateByUserEvent::create)
                .doOnNext(applicationEventPublisher::publishEvent)
                .then();
    }

    private Mono<UserInternalDTO> createUserWithRole(final RegisterDTO dto) {
        return managerRepository.findByEmail(dto.email())
                .flatMap(manager -> createManagerUser(dto, manager))
                .switchIfEmpty(Mono.defer(() -> createRegularUser(dto)));
    }

    private Mono<UserInternalDTO> createRegularUser(final RegisterDTO dto) {
        final UserInternalDTO user = createUserEntity(dto, UserRole.USER);

        return userRepository.save(user);
    }

    private Mono<UserInternalDTO> createManagerUser(
            final RegisterDTO dto,
            final ManagerInternalDTO managerDto
    ) {
        final UserInternalDTO user = createUserEntity(dto, UserRole.MANAGER);

        return saveUserAndUpdateManager(user, managerDto);
    }

    private Mono<UserInternalDTO> saveUserAndUpdateManager(
            final UserInternalDTO user,
            final ManagerInternalDTO managerDto
    ) {
        return userRepository.save(user)
                .map(savedUser -> managerDto.updateUserId(savedUser.userId()))
                .then(managerRepository.save(managerDto))
                .thenReturn(user);
    }

    private UserInternalDTO createUserEntity(
            final RegisterDTO dto,
            final UserRole userRole
    ) {
        final String encodedPassword = passwordEncoder.encode(dto.password().value());

        return UserInternalDTO.create(dto, encodedPassword, userRole);
    }

}