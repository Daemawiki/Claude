package com.daemawiki.internal.core.domain.service.document;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentInternalDTO;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.value.document.DocumentContent;
import com.daemawiki.internal.core.domain.model.value.document.DocumentTitle;
import com.daemawiki.internal.core.domain.model.value.user.Editor;
import com.daemawiki.internal.core.usecase.document.DocumentEditUseCase;
import com.daemawiki.internal.core.domain.component.auth.CurrentUser;
import com.daemawiki.internal.data.repository.document.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Set;
import java.util.function.BiConsumer;

@Service
@Transactional
@RequiredArgsConstructor
class DocumentEditService implements DocumentEditUseCase {

    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    @Override
    @CacheEvict(cacheNames = "documents", key = "#documentId.documentId")
    public Mono<Void> editContents(
            final DocumentId documentId,
            final DocumentContent content
    ) {
        return updateDocument(
                documentId,
                content,
                DocumentInternalDTO::updateContent
        );
    }

    @Override
    @CacheEvict(cacheNames = "documents", key = "#documentId.documentId")
    public Mono<Void> editTitle(
            final DocumentId documentId,
            final DocumentTitle title
    ) {
        return updateDocument(
                documentId,
                title,
                DocumentInternalDTO::updateTitle
        );
    }

    @Override
    @CacheEvict(cacheNames = "documents", key = "#documentId.documentId")
    public Mono<Void> editEditors(
            final DocumentId documentId,
            final Set<Editor> editorSet
    ) {
        return updateDocument(
                documentId,
                editorSet,
                DocumentInternalDTO::updateEditorSet
        );
    }

    private <T> Mono<Void> updateDocument(
            final DocumentId documentId,
            final T updateData,
            final BiConsumer<DocumentInternalDTO, T> updateFunction
    ) {
        return documentRepository.findById(documentId)
                .zipWith(currentUser.get())
                .flatMap(this::validateAccess)
                .doOnNext(document -> updateFunction.accept(document, updateData))
                .flatMap(documentRepository::save)
                .then();
    }

    private Mono<DocumentInternalDTO> validateAccess(final Tuple2<DocumentInternalDTO, UserInternalDTO> tuple) {
        return Mono.just(tuple)
                .filter(t -> t.getT1().isEditor(t.getT2().userId()))
                .switchIfEmpty(Mono.error(CustomExceptionFactory.forbidden("문서에 접근할 권한이 없어요")))
                .map(Tuple2::getT1);
    }

}
