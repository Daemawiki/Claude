package com.daemawiki.internal.core.usecase.document;

import com.daemawiki.internal.core.domain.model.dto.document.DocumentSocketEditOperation;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentSocketEditingUseCase {

    Mono<Void> handleEdit(DocumentSocketEditOperation operation);

    Flux<DocumentSocketEditOperation> subscribeToDocument(DocumentId documentId);

}
