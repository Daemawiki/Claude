package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DocumentSocketEditingUseCase;
import com.daemawiki.daemawiki.application.document.util.FlowContext;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentModel;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import com.daemawiki.daemawiki.interfaces.document.dto.EditOperation;
import com.daemawiki.daemawiki.interfaces.document.dto.request.SubscribeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
class DocumentSocketEditingService implements DocumentSocketEditingUseCase {
    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    private final Map<String, Sinks.Many<EditOperation>> documentSinks = new ConcurrentHashMap<>();
    private final FlowContext flowContext;

    @Override
    public Mono<Void> handleEdit(EditOperation operation) {
        return verifyEditPermission(operation.documentId())
                .flatMap(editor -> {
                    final EditOperation _operation = operation.setUserName(editor.name());

                    getSinkForDocument(_operation.documentId())
                            .tryEmitNext(_operation);

                    return flowContext.getFlow(_operation.documentId())
                            .flatMap(flow -> DocumentOperationHandler.handleOperation(_operation, flow))
                            .then();
                });
    }

    @Override
    public Flux<EditOperation> subscribeToDocument(SubscribeRequest request) {
        return verifyEditPermission(request.documentId())
                .flatMapMany(editor -> getSinkForDocument(request.documentId()).asFlux());
    }

    private Mono<DocumentElementDtos.Editor> verifyEditPermission(String documentId) {
        return documentRepository.findById(documentId)
                .zipWith(currentUser.get())
                .flatMap(documentAndUser -> {
                    final DocumentModel document = documentAndUser.getT1();
                    final UserEntity user = documentAndUser.getT2();

                    final DocumentElementDtos.Editor editor = DocumentElementMapper.fromUserToEditorDto(user);

                    if (!document.canEdit(editor)) {
                        return Mono.error(CustomExceptionFactory.forbidden("해당 문서를 편집할 권한이 없습니다."));
                    }

                    return Mono.just(editor);
                });
    }

    private Sinks.Many<EditOperation> getSinkForDocument(String documentId) {
        return documentSinks.computeIfAbsent(documentId,
                id -> Sinks.many().multicast().onBackpressureBuffer());
    }
}
