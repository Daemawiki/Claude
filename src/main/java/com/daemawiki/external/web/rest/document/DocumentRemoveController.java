package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentRemoveUseCase;
import com.daemawiki.archive.daemawiki.common.annotation.ui.DocumentsRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentRemoveController {

    private final DocumentRemoveUseCase removeUseCase;

    @DeleteMapping("/{documentId}")
    Mono<Void> remove(
            @PathVariable DocumentId documentId
    ) {
        return removeUseCase.remove(documentId);
    }

}
