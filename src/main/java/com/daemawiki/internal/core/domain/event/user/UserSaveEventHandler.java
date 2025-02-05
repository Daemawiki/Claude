package com.daemawiki.internal.core.domain.event.user;

import com.daemawiki.internal.core.domain.model.event.user.UserSaveEvent;
import com.daemawiki.internal.data.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "UserSaveEventHandler")
class UserSaveEventHandler {

    private final UserRepository userRepository;

    @Transactional
    @TransactionalEventListener(UserSaveEvent.class)
    public void save(final UserSaveEvent event) {
        userRepository.save(event.userInternalDTO())
                .subscribe(
                        success -> {},
                        error -> log.error("Error occurred while saving user: ", error)
                );
    }

}
