package com.daemawiki.internal.document.event;

import com.daemawiki.internal.document.dto.DocumentInternalDTO;
import com.daemawiki.internal.user.dto.UserInternalDTO;
import com.daemawiki.internal.user.event.UserSaveEvent;
import com.daemawiki.internal.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "DocumentCreateByUserEventHandler")
class DocumentCreateByUserEventHandler {
    
    private final DocumentRepository documentRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @TransactionalEventListener(DocumentCreateByUserEvent.class)
    public void createByUser(final DocumentCreateByUserEvent event) {
        final UserInternalDTO user = event.userInternalDTO();
        final var document = DocumentInternalDTO.createByUserDto(user);

        documentRepository.save(document)
                .map(d -> user.updateDocumentId(d.documentId()))
                .map(UserSaveEvent::create)
                .doOnNext(applicationEventPublisher::publishEvent)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        success -> {},
                        error -> log.error("Error occurred while creating document: ", error)
                );
    }

}
