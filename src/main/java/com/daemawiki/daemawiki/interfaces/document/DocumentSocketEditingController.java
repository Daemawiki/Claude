package com.daemawiki.daemawiki.interfaces.document;

import com.daemawiki.daemawiki.application.document.usecase.DocumentSocketEditingUseCase;
import com.daemawiki.daemawiki.interfaces.document.dto.EditOperation;
import com.daemawiki.daemawiki.interfaces.document.dto.request.SubscribeRequest;
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
    public Mono<Void> handleEdit(EditOperation operation) {
        return documentSocketEditingUseCase.handleEdit(operation);
    }

    @MessageMapping("document.subscribe")
    public Flux<EditOperation> subscribeToDocument(SubscribeRequest request) {
        return documentSocketEditingUseCase.subscribeToDocument(request);
    }
}
