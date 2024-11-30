package com.daemawiki.daemawiki.interfaces.document.dto;

import java.time.Instant;

public record EditOperation(
    String documentId,
    String userName,
    OperationType type,
    Integer lastElementId,
    String content,
    long timestamp
) {
    public EditOperation {
        timestamp = Instant.now().getEpochSecond();
    }

    public EditOperation setUserName(String userName) {
        return new EditOperation(documentId, userName, type, lastElementId, content, timestamp);
    }

    public enum OperationType {
        INSERT, UPDATE, DELETE
    }
}
