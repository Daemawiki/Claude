package com.daemawiki.internal.core.usecase.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import reactor.core.publisher.Mono;

public interface DocumentRemoveUseCase {

    Mono<Void> remove(DocumentId documentId);

}
