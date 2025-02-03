package com.daemawiki.internal.core.usecase.document;

import com.daemawiki.internal.core.domain.model.primitive.document.DocumentId;
import com.daemawiki.internal.core.domain.model.value.document.DocumentContent;
import com.daemawiki.internal.core.domain.model.value.document.DocumentTitle;
import com.daemawiki.internal.core.domain.model.value.user.Editor;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface DocumentEditUseCase {

    Mono<Void> editContents(
            DocumentId documentId,
            DocumentContent content
    );

    Mono<Void> editTitle(
            DocumentId documentId,
            DocumentTitle title
    );

    Mono<Void> editEditorSet(
            DocumentId documentId,
            Set<Editor> editorSet
    );

}
