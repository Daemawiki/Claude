package com.daemawiki.internal.core.domain.component.document.util;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.data.repository.document.DocumentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
class FlowContextImpl implements FlowContext {

    private final DocumentRepository documentRepository;

    private final HashMap<String, Flow> flows = new HashMap<>();

    @SuppressWarnings("resource")
    @Override
    public Mono<Flow> getFlow(final DocumentId documentId) {
        return Mono.justOrEmpty(this.flows.get(documentId.value()))
                .switchIfEmpty(createFlowByDocumentId(documentId));
    }

    private Mono<Flow> createFlowByDocumentId(final DocumentId documentId) {
        return Mono.defer(() ->
                documentRepository.findById(documentId)
                        .switchIfEmpty(Mono.error(() -> CustomExceptionFactory.notFound("문서를 찾지 못했습니다.")))
                        .map(it -> new FlowImpl(documentId.value(), this)
                                .withCreateElements(it.documentContent().textBody().textBody().lines()))
        );
    }

    @RequiredArgsConstructor
    static class FlowImpl implements Flow {

        private final String documentId;

        private final FlowContextImpl flowContext;

        private int elementIdCounter = 0;

        private boolean isClosed = false;

        @Getter
        private final List<Element> elements = Collections.synchronizedList(new ArrayList<>());

        @Override
        public void createElement(final String content) {
            verifyNotClosed();

            synchronized(this) {
                this.elements.add(elementOf(content));
            }
        }

        @Override
        public void createElement(
                final int lastElementId,
                final String content
        ) {
            verifyNotClosed();

            synchronized(this) {
                this.elements.add(
                        this.elements.indexOf(
                                this.elements.stream()
                                        .filter(it -> it.id() == lastElementId)
                                        .findFirst()
                                        .orElseThrow(() -> new IllegalArgumentException("Element not found"))
                        ),
                        elementOf(content)
                );
            }
        }

        @Override
        public void updateElement(
                final int elementId,
                final String content
        ) {
            verifyNotClosed();

            synchronized(this) {
                this.elements.set(elementId, new ElementImpl(elementId, content));
            }
        }

        @Override
        public void deleteElement(final int elementId) {
            verifyNotClosed();

            synchronized(this) {
                this.elements.remove(elementId);
            }
        }

        private Flow withCreateElements(final Stream<String> contents) {
            verifyNotClosed();

            synchronized(this) {
                contents.forEach(this::createElement);
                return this;
            }
        }

        @Override
        public void close() {
            this.flowContext.flows.remove(this.documentId);
            this.isClosed = true;
        }

        @Override
        public String toString() {
            verifyNotClosed();

            var string = new StringBuilder();
            this.elements.forEach(it -> string.append(it.content()));

            return string.toString();
        }

        private ElementImpl elementOf(final String content) {
            verifyNotClosed();

            return new ElementImpl(this.elementIdCounter++, content);
        }

        private void verifyNotClosed() {
            if (this.isClosed) {
                throw new IllegalStateException("Flow is closed");
            }
        }

    }

    record ElementImpl(
            int id,
            String content
    ) implements Element {
    }

}
