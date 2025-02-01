package com.daemawiki.external.web.rest.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentSocketEditingUseCase;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentSocketEditOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
class DocumentSocketEditingController {
    private final DocumentSocketEditingUseCase documentSocketEditingUseCase;

    @MessageMapping("document.edit")
    public Mono<Void> handleEdit(DocumentSocketEditOperation operation) {
        return documentSocketEditingUseCase.handleEdit(operation);
    }

    @MessageMapping("document.subscribe")
    public Flux<DocumentSocketEditOperation> subscribeToDocument(DocumentId documentId) {
        return documentSocketEditingUseCase.subscribeToDocument(documentId);
    }

}
