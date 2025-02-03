package com.daemawiki.internal.core.domain.service.document;

import com.daemawiki.internal.core.domain.component.document.DocumentOperationHandler;
import com.daemawiki.internal.core.domain.model.dto.user.UserInternalDTO;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.usecase.document.DocumentSocketEditingUseCase;
import com.daemawiki.internal.core.domain.component.document.util.FlowContext;
import com.daemawiki.internal.core.domain.component.auth.CurrentUser;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentSocketEditOperation;
import com.daemawiki.internal.data.repository.document.DocumentRepository;
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

    private final Map<String, Sinks.Many<DocumentSocketEditOperation>> documentSinks = new ConcurrentHashMap<>();
    private final FlowContext flowContext;

    private Disposable schedulerDisposable;

    @Override
    public Mono<Void> handleEdit(final DocumentSocketEditOperation operation) {
        return verifyEditPermission(operation.documentId())
                .flatMap(user -> {
                    final DocumentSocketEditOperation _operation = operation.setUserName(user.personalData().name());

                    getSinkForDocument(_operation.documentId())
                            .tryEmitNext(_operation);

                    return flowContext.getFlow(_operation.documentId())
                            .flatMap(flow -> DocumentOperationHandler.handleOperation(_operation, flow))
                            .then();
                });
    }

    @Override
    public Flux<DocumentSocketEditOperation> subscribeToDocument(final DocumentId documentId) {
        return verifyEditPermission(documentId)
                .flatMapMany(editor -> getSinkForDocument(documentId).asFlux());
    }

    private Mono<UserInternalDTO> verifyEditPermission(final DocumentId documentId) {
        return currentUser.get()
                .zipWith(documentRepository.findById(documentId)
                            .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound("문서를 찾지 못했습니다.")))
                        , (user, document) -> {

                    if (!document.isEditor(user.userId())) {
                        throw CustomExceptionFactory.forbidden("해당 문서를 편집할 권한이 없습니다.");
                    }

                    return user;
                });
    }

    private Sinks.Many<DocumentSocketEditOperation> getSinkForDocument(final DocumentId documentId) {
        return documentSinks.computeIfAbsent(
                documentId.documentId(),
                id -> Sinks.many()
                        .multicast()
                        .onBackpressureBuffer(256, true)
        );
    }

    // TODO: 1/31/25 동시 수정 후 저장 메커니ㅣ즘 필요.
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
    }

    private void cleanupDocument(final String documentId) {
        final Sinks.Many<DocumentSocketEditOperation> sink = documentSinks.get(documentId);

        if (sink != null) {
            sink.tryEmitComplete();
            documentSinks.remove(documentId);
        }
    }

}
