package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.util.FlowContext;
import com.daemawiki.daemawiki.interfaces.document.dto.EditOperation;
import reactor.core.publisher.Mono;

class DocumentOperationHandler {

    public static Mono<Void> handleOperation(EditOperation operation, FlowContext.Flow flow) {
        return Mono.defer(() -> {
            try {
                switch (operation.type()) {
                    case INSERT -> handleInsert(operation, flow);
                    case UPDATE -> handleUpdate(operation, flow);
                    case DELETE -> handleDelete(operation, flow);
                }
                return Mono.empty();
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }

    private static void handleInsert(EditOperation operation, FlowContext.Flow flow) {
        flow.createElement(operation.lastElementId(), operation.content());
    }

    private static void handleUpdate(EditOperation operation, FlowContext.Flow flow) {
        flow.updateElement(operation.lastElementId(), operation.content());
    }

    private static void handleDelete(EditOperation operation, FlowContext.Flow flow) {
        flow.deleteElement(operation.lastElementId());
    }
}
