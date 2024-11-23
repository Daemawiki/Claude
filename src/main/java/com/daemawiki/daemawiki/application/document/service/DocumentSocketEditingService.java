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
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
class DocumentSocketEditingService implements DocumentSocketEditingUseCase {
    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    private final Map<String, Sinks.Many<EditOperation>> documentSinks = new ConcurrentHashMap<>();
    private final FlowContext flowContext;

    // key: {userId}:{documentId}
    private final Map<String, Mono<DocumentElementDtos.Editor>> permissionCache = new ConcurrentHashMap<>();

    private Disposable schedulerDisposable;

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
        return currentUser.get()
                .flatMap(user -> {
                    final String cacheKey = user.getId() + ":" + documentId;

                    return permissionCache.computeIfAbsent(cacheKey, key ->
                            documentRepository.findById(documentId)
                                    .map(document -> verifyUserPermission(user, document))
                                    .cache(Duration.ofMinutes(10))
                                    .doFinally(signal -> permissionCache.remove(cacheKey))
                    );
                });
    }

    private static DocumentElementDtos.Editor verifyUserPermission(final UserEntity user, final DocumentModel document) {
        final DocumentElementDtos.Editor editor = DocumentElementMapper.fromUserToEditorDto(user);

        if (!document.canEdit(editor)) {
            throw CustomExceptionFactory.forbidden("해당 문서를 편집할 권한이 없습니다.");
        }

        return editor;
    }

    private Sinks.Many<EditOperation> getSinkForDocument(String documentId) {
        return documentSinks.computeIfAbsent(documentId,
                id -> Sinks.many().multicast().onBackpressureBuffer(256, true));
    }

    @PostConstruct
    public void cleanUpMemoryScheduler() {
        schedulerDisposable = Flux.interval(Duration.ofMinutes(10))
                .flatMap(tick -> Flux.fromIterable(documentSinks.entrySet()))
                .filter(entry -> entry.getValue().currentSubscriberCount() == 0)
                .doOnNext(entry -> cleanupDocument(entry.getKey()))
                .subscribe();
    }

    @PreDestroy
    public void cleanup() {
        if (schedulerDisposable != null && !schedulerDisposable.isDisposed()) {
            schedulerDisposable.dispose();
        }
        documentSinks.forEach((id, sink) -> sink.tryEmitComplete());
        documentSinks.clear();
        permissionCache.clear();
    }

    private void cleanupDocument(String documentId) {
        final Sinks.Many<EditOperation> sink = documentSinks.get(documentId);
        if (sink != null) {
            sink.tryEmitComplete();
            documentSinks.remove(documentId);
        }

        permissionCache.keySet().removeIf(key -> key.endsWith(":" + documentId));
    }
}
