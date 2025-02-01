package com.daemawiki.internal.core.domain.event.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.event.document.DocumentCreateByUserEvent;
import com.daemawiki.internal.core.domain.model.event.user.UserSaveEvent;
import com.daemawiki.internal.data.repository.document.DocumentRepository;
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
                .map(d -> user.setDocumentId(d.documentId()))
                .map(UserSaveEvent::create)
                .doOnNext(applicationEventPublisher::publishEvent)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        success -> {},
                        error -> log.error("Error occurred while creating document: ", error)
                );
    }

}
