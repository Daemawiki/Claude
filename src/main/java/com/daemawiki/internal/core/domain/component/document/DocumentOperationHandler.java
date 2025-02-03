package com.daemawiki.internal.core.domain.component.document;

import com.daemawiki.internal.core.domain.component.document.util.FlowContext;
import com.daemawiki.internal.core.domain.model.dto.document.DocumentSocketEditOperation;
import reactor.core.publisher.Mono;

public class DocumentOperationHandler {

    public static Mono<Void> handleOperation(
            final DocumentSocketEditOperation operation,
            final FlowContext.Flow flow
    ) {
        return Mono.defer(() -> {
            try {
                switch (operation.type()) {
                    case INSERT -> handleInsert(operation, flow);
                    case UPDATE -> handleUpdate(operation, flow);
                    case DELETE -> handleDelete(operation, flow);
                }
                return Mono.empty();
            } catch (final Exception e) {
                return Mono.error(e);
            }
        });
    }

    private static void handleInsert(
            final DocumentSocketEditOperation operation,
            final FlowContext.Flow flow
    ) {
        flow.createElement(operation.lastElementId().value(), operation.content());
    }

    private static void handleUpdate(
            final DocumentSocketEditOperation operation,
            final FlowContext.Flow flow
    ) {
        flow.updateElement(operation.lastElementId().value(), operation.content());
    }

    private static void handleDelete(
            final DocumentSocketEditOperation operation,
            final FlowContext.Flow flow
    ) {
        flow.deleteElement(operation.lastElementId().value());
    }

}
