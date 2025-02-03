package com.daemawiki.external.rsocket.document;

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
    Mono<Void> handleEdit(final DocumentSocketEditOperation operation) {
        return documentSocketEditingUseCase.handleEdit(operation);
    }

    @MessageMapping("document.subscribe")
    Flux<DocumentSocketEditOperation> subscribeToDocument(final DocumentId documentId) {
        return documentSocketEditingUseCase.subscribeToDocument(documentId);
    }

}
