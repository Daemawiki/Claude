package com.daemawiki.internal.document;

import com.daemawiki.internal.document.primitive.DocumentId;
import reactor.core.publisher.Mono;

public interface DocumentRemoveUseCase {

    Mono<Void> remove(DocumentId documentId);

}
