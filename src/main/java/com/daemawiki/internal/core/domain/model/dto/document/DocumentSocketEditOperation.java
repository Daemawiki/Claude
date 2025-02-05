package com.daemawiki.internal.core.domain.model.dto.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.primitive.shard.ElementId;
import com.daemawiki.internal.core.domain.model.primitive.shard.edit.OperationType;
import com.daemawiki.internal.core.domain.model.primitive.user.personal.Name;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * 해당 클래스는 메모리 사용량, 최적화를 위해 외부에서도 사용할 수 있는 예외적인 클래스입니다.
 */
public record DocumentSocketEditOperation(
    DocumentId documentId,
    Name name,
    OperationType type,
    @JsonProperty("lastElementId")
    ElementId lastElementId,
    String content,
    long timestamp
) {

    public DocumentSocketEditOperation {
        timestamp = Instant.now().getEpochSecond();
    }

    public DocumentSocketEditOperation setUserName(final Name name) {
        return new DocumentSocketEditOperation(documentId, name, type, lastElementId, content, timestamp);
    }

}
