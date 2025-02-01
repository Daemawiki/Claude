package com.daemawiki.archive.daemawiki.application.document.util;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FlowContext {

    Mono<Flow> getFlow(DocumentId documentId);

    interface Flow extends AutoCloseable {

        List<Element> getElements();

        void createElement(String content);

        void createElement(int lastElementId, String content);

        void updateElement(int elementId, String content);

        void deleteElement(int elementId);
    }

    interface Element {

        int id();

        String content();
    }

}
