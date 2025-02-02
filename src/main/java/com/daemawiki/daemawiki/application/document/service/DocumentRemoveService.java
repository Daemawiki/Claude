package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DocumentRemoveUseCase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentModel;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class DocumentRemoveService implements DocumentRemoveUseCase {
    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public Mono<Void> remove(String documentId) {
        return findDocument(documentId)
                .zipWith(currentUser.get())
                .flatMap(tuple -> validateAccess(tuple.getT1(), tuple.getT2()))
                .flatMap(documentRepository::deleteById);
    }

    private Mono<DocumentModel> findDocument(String documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException()));
    }

    private Mono<String> validateAccess(DocumentModel document, UserEntity user) {
        return Mono.just(user)
                .filter(u -> document.canDelete(DocumentElementMapper.fromUserToEditorDto(u)))
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .thenReturn(document.id());
    }
}
