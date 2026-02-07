package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.document.primitive.DocumentId;
import com.daemawiki.internal.document.DocumentRemoveUseCase;
import com.daemawiki.external.web.annotation.ui.DocumentsRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@DocumentsRestApi
@RequiredArgsConstructor
class DocumentRemoveController {

    private final DocumentRemoveUseCase documentRemoveUseCase;

    @DeleteMapping("/{documentId}")
    Mono<Void> remove(
            @PathVariable final DocumentId documentId
    ) {
        return documentRemoveUseCase.remove(documentId);
    }

}
