package com.daemawiki.internal.document;

import com.daemawiki.internal.document.dto.DocumentSocketEditOperation;
import com.daemawiki.internal.document.primitive.DocumentId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentSocketEditingUseCase {

    Mono<Void> handleEdit(DocumentSocketEditOperation operation);

    Flux<DocumentSocketEditOperation> subscribeToDocument(DocumentId documentId);

}
