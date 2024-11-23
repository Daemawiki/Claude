package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.interfaces.document.dto.EditOperation;
import com.daemawiki.daemawiki.interfaces.document.dto.request.SubscribeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentSocketEditingUseCase {
    Mono<Void> handleEdit(EditOperation operation);
    Flux<EditOperation> subscribeToDocument(SubscribeRequest request);
}
