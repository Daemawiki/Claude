package com.daemawiki.daemawiki.application.document.util;

import reactor.core.publisher.Mono;

import java.util.List;

public interface FlowContext {

    Mono<Flow> getFlow(final String documentId);

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
