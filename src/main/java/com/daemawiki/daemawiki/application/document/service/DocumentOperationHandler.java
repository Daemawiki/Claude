package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.util.FlowContext;
import com.daemawiki.daemawiki.interfaces.document.dto.EditOperation;
import reactor.core.publisher.Mono;

class DocumentOperationHandler {

    public static Mono<Void> handleOperation(EditOperation operation, FlowContext.Flow flow) {
        return Mono.defer(() -> {
            try {
                if (operation.type().equals(EditOperation.OperationType.INSERT)) {
                    handleInsert(operation, flow);
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
}
