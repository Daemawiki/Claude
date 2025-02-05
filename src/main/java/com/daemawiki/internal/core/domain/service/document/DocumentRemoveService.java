package com.daemawiki.internal.core.domain.service.document;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentRemoveUseCase;
import com.daemawiki.internal.core.domain.component.auth.CurrentUser;
import com.daemawiki.internal.data.repository.document.DocumentRepository;
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
    public Mono<Void> remove(final DocumentId documentId) {
        return findDocument(documentId)
                .zipWith(currentUser.get())
                .flatMap(tuple -> validateAccess(tuple.getT1(), tuple.getT2()))
                .flatMap(documentRepository::deleteById);
    }

    private Mono<DocumentInternalDTO> findDocument(final DocumentId documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound("문서를 찾지 못했습니다.")));
    }

    private Mono<DocumentId> validateAccess(
            final DocumentInternalDTO document,
            final UserInternalDTO user
    ) {
        return Mono.just(user)
                .filter(u -> document.isOwner(u.userId()))
                .switchIfEmpty(Mono.error(CustomExceptionFactory.forbidden("문서를 삭제할 권한이 없습니다.")))
                .thenReturn(document.documentId());
    }

}
